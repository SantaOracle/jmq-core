package com.springmvc.controller;

import com.springmvc.activemq.consumer.IConsumer;
import com.springmvc.activemq.consumer.QueueConsumer;
import com.springmvc.activemq.producer.IProducer;
import com.springmvc.activemq.producer.Producer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Controller
@RequestMapping("/mq")
public class MQTestController {

    @Resource
    private JmsTemplate jmsTemplate;
    @Resource
    private Producer producer;
    @Resource
    private QueueConsumer queueConsumer;

    private IProducer jmqProducer;
    private IConsumer jmqConsumer;

    @PostConstruct
    public void init(){
        jmqProducer = producer;
        jmqConsumer = queueConsumer;
    }

    @RequestMapping("/sendText")
    @ResponseBody
    public String sendText(
            @RequestParam(value = "msg", required = true)String msg
    ){
        if (jmsTemplate == null){
            System.out.println("jmsTemplate is null");
        }
        jmqProducer.sendText(msg);
        return "success";
    }

    @RequestMapping("/reciveText")
    @ResponseBody
    public String reciveText(){
        jmqConsumer.reciveText();
        return "success";
    }
}
