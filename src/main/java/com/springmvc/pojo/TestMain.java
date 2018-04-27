package com.springmvc.pojo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {
    public static void main(String args[]) {
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        Object user= context.getBean("jmsTemplate");
        String[] str=context.getBeanDefinitionNames();
        for (String string : str) {
            System.out.println("..."+string);
        }
        System.out.println("----"+user);

    }
}
