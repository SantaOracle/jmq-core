package com.springmvc.activemq.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DomainListener implements ApplicationContextAware, MessageListener{

    private List<IMQListener> listenerCache;

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void init(){
        Map<String, IMQListener> listenerMap = applicationContext.getBeansOfType(IMQListener.class);
        listenerCache = new ArrayList<IMQListener>();
        for (IMQListener listener : listenerMap.values()){
            listenerCache.add(listener);
        }
    }

    public void onMessage(Message message) {
        for (IMQListener listener : listenerCache){
            listener.onMessage(message);
        }
    }
}
