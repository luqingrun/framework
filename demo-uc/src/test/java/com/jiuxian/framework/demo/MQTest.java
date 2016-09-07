package com.jiuxian.framework.demo;


import com.jiuxian.demo.module.uc.entity.UcUser;
import com.jiuxian.framework.mq.RabbitMessage;
import com.jiuxian.framework.mq.RabbitProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by luqingrun on 16/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class MQTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    RabbitProducer rabbitProducer;

    @Test
    public void test() throws IOException {
        for(int i =0 ;i< 10;i++) {
            UcUser user = new UcUser();
            user.setName("name"+i);
            RabbitMessage rabbitMessagec = new RabbitMessage("fuck_c", user);
            rabbitProducer.send(rabbitMessagec);
        }

        System.in.read();
    }


}
