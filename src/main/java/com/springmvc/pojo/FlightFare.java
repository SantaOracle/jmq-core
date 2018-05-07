package com.springmvc.pojo;

import java.util.Date;

public class FlightFare {

    private Integer id;     //id

    private String linkKey; //flight_fare和flight_fare_source关联字段，由UUID随机生成

    private String dep;     //出发地，机场三字码，大写

    private String arr;     //目的地，机场三字码，大写

    private String depTime;   //起飞时间，如：2018-05-06 17：00，录入时为了方便可以写成2018-05-06_17:00

    private Integer fare;   //机票价格

    private Character validate;  //机票信息是否有效，Y/N

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLinkKey() {
        return linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
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
        this.depTime = depTime == null ? null : depTime.replaceAll("_", " ");
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public Character getValidate() {
        return validate;
    }

    public void setValidate(Character validate) {
        this.validate = validate;
    }
}
