package com.jiuxian.framework.mq.impl;

import com.jiuxian.framework.mq.RabbitMessage;
import com.jiuxian.framework.mq.RabbitProducer;
import com.jiuxian.framework.spring.ApplicationContextHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by luqingrun on 16/8/22.
 */
@Component("rabbitProducer")
public class RabbitProducerImpl implements RabbitProducer {
    private static Log log = LogFactory.getLog(RabbitProducerImpl.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitTemplate getRabbitTemplate() {
        if (null == rabbitTemplate) {
            rabbitTemplate = ApplicationContextHelper.getBean(RabbitTemplate.class);
        }
        return rabbitTemplate;
    }

    @Override
    public void send(RabbitMessage message) {
        MessageProperties messageProperties = new MessageProperties();
        String msgId = UUID.randomUUID().toString();
        messageProperties.setMessageId(msgId);
        Message message1 = getRabbitTemplate().getMessageConverter().toMessage(message.getPayload(), messageProperties);
        if(StringUtils.isBlank(message.getExchange())){
            getRabbitTemplate().send(message.getRouteKey(), message1);
        }else {
            getRabbitTemplate().send(message.getExchange(), message.getRouteKey(), message1);
        }
        log.info("send rabbitmsg("+msgId+") success");
    }

}
