package com.springmvc.activemq.producer;

import javax.jms.Destination;

public interface IProducer {
    void sendText(String textMsg);

//    void sendText(Destination destination, String textMsg);
}
