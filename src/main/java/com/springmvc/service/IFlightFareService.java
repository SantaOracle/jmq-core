package com.springmvc.service;

import com.springmvc.pojo.FlightFare;

import java.util.List;

public interface IFlightFareService {

    //根据条件（dep, arr, dep_time）查询Fare
    List<FlightFare> searchFareByCondition(FlightFare condition);

}
