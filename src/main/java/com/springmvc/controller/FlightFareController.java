package com.springmvc.controller;

import com.springmvc.dao.FlightFareDao;
import com.springmvc.pojo.FlightFare;
import com.springmvc.vo.FlightFareVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/flightfare")
public class FlightFareController {

    @Resource
    private FlightFareDao flightFareDao;

    @RequestMapping("/insert")
    @ResponseBody
    public int testInsert(
            @RequestParam(value = "linkKey", required = false)String linkKey,
            @RequestParam(value = "dep", required = true)String dep,
            @RequestParam(value = "arr", required = true)String arr,
            @RequestParam(value = "depTime", required = true)String depTime,
            @RequestParam(value = "fare", required = true)Integer fare,
            @RequestParam(value = "validate", required = false)Character validate
    ){
        FlightFare flightFare = new FlightFare();

        if (linkKey != null){
            flightFare.setLinkKey(linkKey);
        }else{
            flightFare.setLinkKey(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        }
        flightFare.setDep(dep.toUpperCase());
        flightFare.setArr(arr.toUpperCase());
        flightFare.setDepTime(depTime);
        flightFare.setFare(fare);
        if (validate != null){
            flightFare.setValidate(validate);
        }else{
            flightFare.setValidate('Y');
        }

        return flightFareDao.insert(flightFare);
    }

    @RequestMapping("/selectByLinkKey")
    @ResponseBody
    public FlightFareVO selectByLinkKey(
            @RequestParam(value = "linkKey", required = true)String linkKey
    ){
        FlightFare flightFare = flightFareDao.selectByLinkKey(linkKey);
        return new FlightFareVO(flightFare);
    }

    @RequestMapping("/selectByCondition")
    @ResponseBody
    public List<FlightFareVO> selectByCondition(
            @RequestParam(value = "dep", required = true)String dep,
            @RequestParam(value = "arr", required = true)String arr,
            @RequestParam(value = "depTime", required = true)String depTime
    ){
        FlightFare condition = new FlightFare();
        condition.setDep(dep);
        condition.setArr(arr);
        condition.setDepTime(depTime);
        List<FlightFare> resultList = flightFareDao.selectByCondition(condition);
        List<FlightFareVO> voList = new ArrayList<FlightFareVO>();
        for (FlightFare result : resultList){
            FlightFareVO vo = new FlightFareVO(result);
            voList.add(vo);
        }
        return voList;
    }

    @RequestMapping("/updateByLinkKey")
    @ResponseBody
    public int updateByLinkKey(
            @RequestParam(value = "linkKey", required = true)String linkKey,
            @RequestParam(value = "dep", required = false)String dep,
            @RequestParam(value = "arr", required = false)String arr,
            @RequestParam(value = "depTime", required = false)String depTime,
            @RequestParam(value = "fare", required = false)Integer fare,
            @RequestParam(value = "validate", required = false)Character validate
    ){
        FlightFare condition = new FlightFare();

        condition.setLinkKey(linkKey);
        condition.setDep(dep);
        condition.setArr(arr);
        condition.setDepTime(depTime);
        condition.setFare(fare);
        condition.setValidate(validate);

        return flightFareDao.updateByLinkKey(condition);

    }
}
