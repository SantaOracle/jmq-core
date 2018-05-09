package com.springmvc.activemq.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;

@Component
public class Producer implements IProducer{

    @Resource
    private JmsTemplate jmsTemplate;        //default JmsTemplate, default destination is queue

    public void sendText(final String textMsg) {
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                System.out.println("准备发送文字消息：" + textMsg);
                TextMessage msg = session.createTextMessage(textMsg);
                return msg;
            }
        });
    }
}
