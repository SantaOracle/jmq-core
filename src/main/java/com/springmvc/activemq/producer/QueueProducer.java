package com.springmvc.activemq.producer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class QueueProducer implements IProducer{

    @Resource
    private JmsTemplate jmsTemplate;        //default JmsTemplate

    public void sendText(final String textMsg) {

        if (jmsTemplate == null){
            ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
            jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        }

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                System.out.println("准备发送文字消息：" + textMsg);
                TextMessage msg = session.createTextMessage(textMsg);
                return msg;
            }
        });
    }

}
