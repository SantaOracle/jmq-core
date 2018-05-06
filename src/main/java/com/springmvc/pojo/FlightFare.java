package com.springmvc.pojo;

import java.util.Date;

public class FlightFare {

    private int id;     //id

    private String linkKey; //flight_fare和flight_fare_source关联字段，由UUID随机生成

    private String dep;     //出发地，机场三字码，大写

    private String arr;     //目的地，机场三字码，大写

    private String depTime;   //起飞时间，如：2018-05-06 17：00，录入时为了方便可以写成2018-05-06_17:00

    private int fare;   //机票价格

    private char validate;  //机票信息是否有效，Y/N

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.depTime = depTime.replaceAll("_", " ");
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public char getValidate() {
        return validate;
    }

    public void setValidate(char validate) {
        this.validate = validate;
    }
}
