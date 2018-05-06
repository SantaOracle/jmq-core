package com.springmvc.activemq.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import sun.security.util.Cache;

import javax.annotation.Resource;

@Component
public class QueueConsumer implements IConsumer{

    @Resource
    private JmsTemplate jmsTemplate;    //default JmsTemplate

    public void reciveText() {

        if (jmsTemplate == null){
            ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
            jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        }

        String msg = (String) jmsTemplate.receiveAndConvert();
        System.out.println("收到文字消息：" + msg);
    }
}
