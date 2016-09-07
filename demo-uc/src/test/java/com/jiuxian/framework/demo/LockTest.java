package com.jiuxian.framework.demo;

import com.jiuxian.framework.cache.impl.RedisCacheServiceImpl;
import com.jiuxian.framework.lock.LockArithmetic;
import com.jiuxian.framework.util.page.Pager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by luqingrun on 16/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class LockTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    LockArithmetic lockArithmetic;

    @Test
    public void test() throws IOException {

        for(int i=0;i<10;i++){
            Thread thread = new Thread(){
                @Override
                public void run(){
                    System.out.println(Thread.currentThread().getName()+ ",lock:" +lockArithmetic.lock("testLock"));
                    try {
                        Thread.sleep(new Random().nextInt(1000*3));
                        System.out.println(Thread.currentThread().getName()+ ",unlock:" +lockArithmetic.unLock("testLock"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.setName(""+i);
            thread.start();
        }
        System.in.read();
    }


}
