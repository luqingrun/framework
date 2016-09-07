package com.jiuxian.framework.mq;

public interface RabbitProducer{
    void send(RabbitMessage message);
}