package com.springmvc.service;

import com.springmvc.pojo.FlightFare;

import java.util.List;

public interface FlightFareService {

    List<FlightFare> searchFareByCondition(FlightFare condition);

}
