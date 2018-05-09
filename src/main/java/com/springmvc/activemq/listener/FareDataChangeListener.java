package com.springmvc.activemq.listener;

import com.springmvc.service.impl.FlightFareServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class FareDataChangeListener implements IMQListener{

    @Resource
    private FlightFareServiceImpl flightFareService;

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.println("监听器接收到文字消息：" + text);
            flightFareService.dataOnchange(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
