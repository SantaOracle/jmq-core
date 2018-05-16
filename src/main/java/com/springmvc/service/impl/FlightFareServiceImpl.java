package com.springmvc.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.springmvc.dao.FlightFareDao;
import com.springmvc.pojo.FlightFare;
import com.springmvc.service.IFlightFareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 上层缓存key：dep+arr+depTime，例如：PEKHKG20180508
 */
@Service
public class FlightFareServiceImpl implements IFlightFareService {

    @Resource
    private FlightFareDao flightFareDao;

    @Resource
    private FlightFareSourceServiceImpl flightFareSourceService;

    //上层缓存，为了提升QPS而设计
    private Cache<String, List<FlightFare>> cache = Caffeine.newBuilder()
            .maximumSize(5000)
            .removalListener(new RemovalListener<String, List<FlightFare>>() {
                public void onRemoval(String key, List<FlightFare> datas, RemovalCause removalCause) {
                    if (removalCause.equals(RemovalCause.EXPLICIT)){
                        System.out.println("缓存数据由于更新被移除，key：" + key);
                    }
                }
            }).build();

    private ScheduledExecutorService delayInvalidCachePool = Executors.newScheduledThreadPool(1);

    public static String ADD_INDEX = "1";

    public static String UPDATE_INDEX = "2";

    /**
     * 按照dep、arr、depTime查询机票报价
     * @param condition
     * @return
     */
    public List<FlightFare> searchFareByCondition(FlightFare condition) {

        //验证条件参数
        if (!validateCondition(condition)){
            System.out.println("查询参数有误！");
            return Collections.EMPTY_LIST;
        }

        //优先查缓存
        String cacheKey = generateCacheKey(condition);
        List<FlightFare> result = cache.getIfPresent(cacheKey);
        if (result != null){
            //cache hit
            return result;
        }

        //cache miss
        result = flightFareDao.selectByCondition(condition);
        if (result != null){
            filterResult(result);
            if (result.size() > 0){
                cache.put(cacheKey, result);
            }
            return result;
        }else{
            return Collections.EMPTY_LIST;
        }


    }

    /**
     * 接收mq消息，同步flight_fare和flight_fare_source数据库表数据
     * msg内容为 操作位-linkKey，比如：1-c69a1e6e7fbb43a899f41621caa46144
     * 该linkKey对应的FlightFare在flight_fare_source表中一定存在（只不过validate不一定为Y）
     * 数据同步过程：
     * @param msg
     */
    public void dataOnchange(String msg){
        List<String> msgList = Arrays.asList(msg.split("-"));
        FlightFare newData = flightFareSourceService.querySourceByLinkKey(msgList.get(1));
        String invalidCacheKey;
        if (msgList.get(0).equals(FlightFareServiceImpl.ADD_INDEX)){
            flightFareDao.insert(newData);
            invalidCacheKey = generateCacheKey(newData);
        }else{
            FlightFare oldData = flightFareDao.selectByLinkKey(msgList.get(1));
            invalidCacheKey = generateCacheKey(oldData);
            flightFareDao.updateByLinkKey(newData);
        }
        delayExpireCache(invalidCacheKey);
    }

    private void filterResult(List<FlightFare> result){
        Iterator<FlightFare> it = result.iterator();
        while (it.hasNext()){
            FlightFare data = it.next();
            if (data.getValidate() != null && data.getValidate() == 'N'){
                it.remove();
            }
        }
    }

    /**
     * 验证查询参数
     * @param condition
     * @return
     */
    private boolean validateCondition(FlightFare condition){
        boolean result = true;
        result = condition.getDep() != null ? true : false;
        result = condition.getArr() != null ? true : false;
        result = condition.getDepTime() != null && condition.getDepTime().length() == 10 ? true : false;
        return result;
    }

    /**
     * 生成缓存的key
     * @param condition
     * @return
     */
    private String generateCacheKey(FlightFare condition){
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(condition.getDep().toUpperCase())
                .append(condition.getArr().toUpperCase())
                .append(condition.getDepTime().replaceAll("-", ""));
        return keyBuilder.toString();
    }

    /**
     * 延时执行缓存失效，避免因线程安全问题导致脏数据留在缓存中
     */
    private void delayExpireCache(final String key){
        delayInvalidCachePool.schedule(new Runnable() {
            public void run() {
                expireCache(key);
            }
        }, 3, TimeUnit.SECONDS);
    }

    /**
     * 缓存失效执行
     * @param key
     */
    private void expireCache(String key){
        cache.invalidate(key);
    }
}
