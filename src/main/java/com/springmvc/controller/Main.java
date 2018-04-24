package com.springmvc.controller;

import com.springmvc.pojo.Admin;
import com.springmvc.service.impl.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    @Resource
    private AdminService adminService;

    @RequestMapping("/")
    @ResponseBody
    public String init(){
        return "Hello world!";
    }

    @RequestMapping(value = "/admin",method = RequestMethod.POST)
    @ResponseBody
    public int insertUser(Admin admin){
        return adminService.insertAdmin(admin);

    }
    @RequestMapping(value = "/admin/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Admin getUser(@PathVariable Integer id){
        return adminService.getAdmin(id);

    }
}
