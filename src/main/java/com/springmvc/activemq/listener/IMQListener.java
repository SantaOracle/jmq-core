package com.springmvc.activemq.listener;

import javax.jms.Message;

public interface IMQListener {

    void onMessage(Message message);
}
