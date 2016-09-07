package com.jiuxian.framework.mq;

import com.jiuxian.framework.util.json.JsonUtils;

import java.io.Serializable;

public class RabbitMessage implements Serializable{
    private static final long serialVersionUID = 1070328618684292533L;

    //交换器
    private String exchange;
    //路由key
    private String routeKey;
    //消息体
    private Object payload;

    public RabbitMessage(String exchange, String routeKey, Object payload){
        this.exchange = exchange;
        this.routeKey = routeKey;
        this.payload = payload;
    }

    public RabbitMessage(String routeKey, Object payload){
        this.routeKey = routeKey;
        this.payload = payload;
    }


    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "RabbitMessage{" +
                "exchange='" + exchange + '\'' +
                ", routeKey='" + routeKey + '\'' +
                ", payload=" + JsonUtils.objectToJson(payload) +
                '}';
    }
}
