package com.springmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    @RequestMapping("/")
    @ResponseBody
    public String init(){
        return "Hello world!";
    }

}
