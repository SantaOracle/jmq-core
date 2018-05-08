package com.springmvc.service;

import com.springmvc.pojo.FlightFare;

public interface IFlightFareSourceService {

    //增加一条FlightFareSource，API for source client
    int addFlightFareSource(FlightFare flightFare);

    //修改一条FlightFareSource，API for source client
    int updateFlightFareSource(FlightFare flightFare);

    //按照LinkKey查询一条FlightFareSource，API for all client
    FlightFare querySourceByLinkKey(String linkKey);
}
