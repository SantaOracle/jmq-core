package com.springmvc.activemq.consumer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueueConsumer implements IConsumer{

    @Resource
    private JmsTemplate jmsTemplate;    //default JmsTemplate

    public void reciveText() {
        String msg = (String) jmsTemplate.receiveAndConvert();
        System.out.println("收到文字消息：" + msg);
    }
}
