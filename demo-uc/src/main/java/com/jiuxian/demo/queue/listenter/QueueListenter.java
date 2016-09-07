package com.jiuxian.demo.queue.listenter;


import com.jiuxian.framework.mq.RabbitListener;
import com.jiuxian.framework.util.json.JsonUtils;

public class QueueListenter extends RabbitListener {


    @Override
    public void onRabbitMessage(Object payload) {
        System.out.println(JsonUtils.objectToJson(payload));
    }
}