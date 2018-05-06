package com.springmvc.dao;

import com.springmvc.pojo.FlightFare;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FlightFareDao {

    int insert(FlightFare flightFare);

    int updateByLinkKey(FlightFare flightFare);

    FlightFare selectByLinkKey(String linkKey);

    List<FlightFare> selectByCondition(FlightFare flightFare);

}
