package com.springmvc.test;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TestMain {

    private Cache<String, String> cache = Caffeine.newBuilder().maximumSize(5000).build();



    public static void main(String args[]) throws ParseException {
       String s = "2018-05-06_17:30";
       System.out.println(s.replaceAll("_", " "));
       String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
       System.out.println(uuid);


    }


}
