package com.jiuxian.framework.cache.impl;

import com.danga.MemCached.MemCachedClient;

import com.jiuxian.framework.cache.CacheService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * 需要在spring配置文件中声明pool
 *<!-- Memcached配置 -->
 <bean class="com.danga.MemCached.SockIOPool"
 factory-method="getInstance" init-method="initialize" destroy-method="shutDown">
 <property name="servers">
 <list>
 <value>${memcached.server}</value>
 </list>
 </property>
 <property name="initConn">
 <value>${memcached.initConn}</value>
 </property>
 <property name="minConn">
 <value>${memcached.minConn}</value>
 </property>
 <property name="maxConn">
 <value>${memcached.maxConn}</value>
 </property>
 <property name="maintSleep">
 <value>${memcached.maintSleep}</value>
 </property>
 <property name="nagle">
 <value>${memcached.nagle}</value>
 </property>
 <property name="socketTO">
 <value>${memcached.socketTO}</value>
 </property>
 </bean>
 */

//@Component("memcachedCache")
public class MemcachedCacheServiceImpl implements CacheService, InitializingBean {

    private MemCachedClient client = new MemCachedClient();

    @Value("${memcached.namespace}")
    private String namespace;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public boolean set(String key, Object value, int expireSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expireSecond);
        return client.set(key, value, calendar.getTime());
    }

    @Override
    public Object get(String key) {
        return client.get(key);
    }

    @Override
    public Object delete(String key) {
        Object object = get(key);
        client.delete(key);
        return object;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        client.setDefaultEncoding("utf-8");
    }

    public MemCachedClient getClient() {
        return client;
    }
}
