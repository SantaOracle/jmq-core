package com.springmvc.activemq.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;

@Component
public class TextProducer implements IProducer{

    @Resource
    private JmsTemplate jmsTemplate;        //default JmsTemplate, default destination is topic

    public void sendText(final String textMsg) {
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                System.out.println("TextProducer ready to send text message：" + textMsg);
                TextMessage msg = session.createTextMessage(textMsg);
                return msg;
            }
        });
    }
}
