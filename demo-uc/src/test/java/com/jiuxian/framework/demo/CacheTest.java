package com.jiuxian.framework.demo;

import com.jiuxian.framework.cache.impl.RedisCacheServiceImpl;
import com.jiuxian.framework.util.page.Pager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqingrun on 16/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class CacheTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    RedisCacheServiceImpl redisCacheService;

    @Test
    public void test() {
        Pager pager = new Pager(10,10);
        redisCacheService.set("page", pager);
        System.out.println(pager);
        Assert.assertEquals(pager, redisCacheService.get("page"));
        System.out.println(redisCacheService.get("page"));


        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);


        redisCacheService.set("list" , list);
        System.out.println(redisCacheService.get("list", List.class));
    }


}
