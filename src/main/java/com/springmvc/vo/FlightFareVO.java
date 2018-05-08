package com.springmvc.vo;

import com.springmvc.pojo.FlightFare;

import java.util.ArrayList;
import java.util.List;

public class FlightFareVO {

    private String dep;

    private String arr;

    private String depTime;

    private int fare;

    public FlightFareVO(){}

    public FlightFareVO(FlightFare flightFare){
        this.dep = flightFare.getDep();
        this.arr = flightFare.getArr();
        this.depTime = flightFare.getDepTime();
        this.fare = flightFare.getFare();
    }

    public static List<FlightFareVO> generateVOList(List<FlightFare> flightFares){
        List<FlightFareVO> voList = new ArrayList<FlightFareVO>();
        for (FlightFare flightFare : flightFares){
            FlightFareVO vo = new FlightFareVO(flightFare);
            voList.add(vo);
        }
        return voList;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr = arr;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }
}
