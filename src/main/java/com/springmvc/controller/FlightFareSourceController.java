package com.springmvc.controller;

import com.springmvc.pojo.FlightFare;
import com.springmvc.service.impl.FlightFareSourceServiceImpl;
import com.springmvc.vo.FlightFareVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/flightfareSource")
public class FlightFareSourceController {

    @Resource
    private FlightFareSourceServiceImpl flightFareSourceService;

    @RequestMapping("/insert")
    @ResponseBody
    public int insertData(
            @RequestParam(value = "dep", required = true)String dep,
            @RequestParam(value = "arr", required = true)String arr,
            @RequestParam(value = "depTime", required = true)String depTime,
            @RequestParam(value = "fare", required = true)Integer fare,
            @RequestParam(value = "validate", required = true)Character validate
    ){
        FlightFare flightFare = new FlightFare();

        flightFare.setDep(dep.toUpperCase());
        flightFare.setArr(arr.toUpperCase());
        flightFare.setDepTime(depTime);
        flightFare.setFare(fare);
        flightFare.setValidate(validate);

        return flightFareSourceService.addFlightFareSource(flightFare);
    }

    @RequestMapping("/updateByLinkKey")
    @ResponseBody
    public int updateDataByLinkKey(
            @RequestParam(value = "linkKey", required = true)String linkKey,
            @RequestParam(value = "dep", required = false)String dep,
            @RequestParam(value = "arr", required = false)String arr,
            @RequestParam(value = "depTime", required = false)String depTime,
            @RequestParam(value = "fare", required = false)Integer fare,
            @RequestParam(value = "validate", required = false)Character validate
    ){
        FlightFare flightFare = new FlightFare();

        flightFare.setLinkKey(linkKey);
        flightFare.setDep(dep.toUpperCase());
        flightFare.setArr(arr.toUpperCase());
        flightFare.setDepTime(depTime);
        flightFare.setFare(fare);
        flightFare.setValidate(validate);

        return flightFareSourceService.updateFlightFareSource(flightFare);
    }

    @RequestMapping("/queryByLinkKey")
    @ResponseBody
    @Deprecated
    public FlightFareVO queryFlightFareByLinkKey(
            @RequestParam(value = "linkKey", required = true)String linkKey
    ){
        FlightFare result = flightFareSourceService.querySourceByLinkKey(linkKey);
        return new FlightFareVO(result);
    }
}
