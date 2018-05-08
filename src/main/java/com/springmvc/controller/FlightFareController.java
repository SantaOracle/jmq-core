package com.springmvc.controller;

import com.springmvc.pojo.FlightFare;
import com.springmvc.service.impl.FlightFareServiceImpl;
import com.springmvc.vo.FlightFareVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/flightfare")
public class FlightFareController {

    @Resource
    private FlightFareServiceImpl flightFareService;

    @RequestMapping("/query")
    @ResponseBody
    public List<FlightFareVO> queryFlightFare(
            @RequestParam(value = "dep", required = true)String dep,
            @RequestParam(value = "arr", required = true)String arr,
            @RequestParam(value = "depTime", required = true)String depTime
    ){
        FlightFare condition = new FlightFare();
        condition.setDep(dep);
        condition.setArr(arr);
        condition.setDepTime(depTime);
        List<FlightFare> resultList = flightFareService.searchFareByCondition(condition);
        return FlightFareVO.generateVOList(resultList);
    }
}
