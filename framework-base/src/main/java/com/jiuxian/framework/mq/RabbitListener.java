package com.jiuxian.framework.mq;


import com.jiuxian.framework.spring.ApplicationContextHelper;
import com.jiuxian.framework.util.json.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public abstract class RabbitListener implements MessageListener {

    private static Log log = LogFactory.getLog(RabbitListener.class);

    private RabbitTemplate rabbitTemplate;


    public RabbitTemplate getRabbitTemplate() {
        if(null == rabbitTemplate){
            rabbitTemplate = ApplicationContextHelper.getBean(RabbitTemplate.class);
        }
        return rabbitTemplate;
    }

    public abstract void onRabbitMessage(Object payload);

    @Override
    public void onMessage(Message message){
        log.info("recive rabbitmsg("+message.getMessageProperties().getMessageId()+") success");
        Object o = getRabbitTemplate().getMessageConverter().fromMessage(message);
        try {
            onRabbitMessage(o);
        }catch (Exception e){
            log.error("onRabbitMessage error rabbitmsg("+message.getMessageProperties().getMessageId()+") error, messageProperties:" + message.getMessageProperties().toString() + ", payload:"+ JsonUtils.objectToJson(o));
            throw e;
        }
    }
}
