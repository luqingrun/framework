package com.jiuxian.framework.demo;



import com.jiuxian.demo.module.uc.entity.UcUser;
import com.jiuxian.demo.module.uc.service.UcUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.*;

/**
 * Created by luqingrun on 16/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class UserTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UcUserService ucUserService;

    @Test
    public void testInsert() throws IOException {
        UcUser ucUser = new UcUser();
        ucUser.setTicket(UUID.randomUUID().toString());
        ucUser.setLoginName("loginName1");
        ucUser.setName("姓名1");
        ucUser.setAddTime(new Date());
        ucUser.setEmail("luqingrun@jiuxian.com");
        ucUser.setPasswd("passwd");
        ucUser.setPhone("13331156897");

        //ucUserService.insert(ucUser);

        //System.out.println(ucUser.getPkid());

        List<Object> list = new ArrayList<>();
        list.add("adfasd");
        list.add("adafsdf");
        list.add(378588);
        Map<String, Object> map = new HashMap<>();
//        map.put("name", "test304244");
//        map.put("login_name", "loginName304244") ;
        map.put("pkid", list);
//        map.put("passwd", 111);

        System.out.println(ucUserService.pageByProperties(map, 1));


        System.in.read();
    }

}
