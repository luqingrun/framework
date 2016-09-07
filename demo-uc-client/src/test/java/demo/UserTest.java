package demo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jiuxian.demo.module.uc.entity.UcUser;
import com.jiuxian.demo.module.uc.service.UcUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;
import java.util.UUID;

/**
 * Created by luqingrun on 16/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class UserTest extends AbstractJUnit4SpringContextTests {

    @Reference(version = "*")
    private UcUserService userService;


    @Test
    public void testInsert() throws InterruptedException {
        runDubbo();

    }

    public void runDubbo(){
        int i = 0;
        while (true) {
            try {
                UcUser user = new UcUser();
                user.setName("test" + i);
                user.setPasswd("passwd1");
                user.setTicket(UUID.randomUUID().toString());
                user.setLoginName("loginName" + i);
                System.out.println(Thread.currentThread().getName() + "_insert user==>" + userService.insert(user));
                Thread.currentThread().sleep(new Random().nextInt(100));
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
