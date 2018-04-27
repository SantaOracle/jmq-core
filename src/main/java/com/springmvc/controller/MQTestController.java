package com.springmvc.controller;

import com.springmvc.activemq.consumer.IConsumer;
import com.springmvc.activemq.consumer.QueueConsumer;
import com.springmvc.activemq.producer.IProducer;
import com.springmvc.activemq.producer.QueueProducer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mq")
public class MQTestController {

    @Resource
    private JmsTemplate jmsTemplate;

    private IProducer producer = new QueueProducer();
    private IConsumer consumer = new QueueConsumer();

    @RequestMapping("/sendText")
    @ResponseBody
    public String sendText(
            @RequestParam(value = "msg", required = true)String msg
    ){
        if (jmsTemplate == null){
            System.out.println("jmsTemplate is null");
        }
        producer.sendText(msg);
        return "success";
    }

    @RequestMapping("/reciveText")
    @ResponseBody
    public String reciveText(){
        consumer.reciveText();
        return "success";
    }
}
