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
        filterResult(result);
        cache.put(cacheKey, result);
        return result;
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
