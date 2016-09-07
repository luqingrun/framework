package com.jiuxian.framework.demo;

import com.jiuxian.framework.cache.CacheService;
import com.jiuxian.framework.cache.impl.RedisCacheServiceImpl;
import com.jiuxian.framework.lock.LockArithmetic;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Random;

/**
 * Created by luqingrun on 16/8/11.
 */
public class Main {

    public static volatile boolean running = true;

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");

//        CacheService cacheService = app.getBean("redisCache", CacheService.class);
//        cacheService.set("test", cacheService , 10);
//        System.out.println(cacheService.get("test"));
        LockArithmetic lockArithmetic = app.getBean(LockArithmetic.class);



        System.in.read();
    }
}
