package com.jiuxian.framework.lock.impl;

import com.jiuxian.framework.lock.LockArithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("redisLock")
public class RedisLockArithmetic implements LockArithmetic {

    private Logger logger = LoggerFactory.getLogger(RedisLockArithmetic.class);
    @Autowired
    private RedisComponent redisComponent;

     /**
     * 休眠时长，以毫秒为单位
     * 默认为100毫秒
     */
    private long sleeptime = 100L;

    public void setRedisComponent(RedisComponent redisComponent) {
        this.redisComponent = redisComponent;
    }

    public void setSleeptime(long sleeptime) {
        this.sleeptime = sleeptime;
    }

    @Override
    public boolean lock(String key, long timeOutMillisecond) {
        while(true) {
            // 当前加锁时间
            long currentLockTime = System.currentTimeMillis() + timeOutMillisecond + 1;
            if(redisComponent.setIfAbsent(key, currentLockTime)) {
                // 获取锁成功
                return true;
            } else {
                //其他线程占用了锁
                Long otherLockTime = redisComponent.get(key);
                if(otherLockTime == null) {
                    // 其他系统释放了锁
                    // 立刻重新尝试加锁
                    continue;
                } else {
                    if(System.currentTimeMillis() > otherLockTime) {
                        //锁超时
                        //尝试更新锁
                        Long otherLockTime2 = redisComponent.getAndSet(key, currentLockTime);
                        if(otherLockTime2 == null || otherLockTime.equals(otherLockTime2)) {
                            return true;
                        } else {
                            sleep();
                            //重新尝试加锁
                            continue;
                        }
                    } else {
                        //锁未超时
                        sleep();
                        //重新尝试加锁
                        continue;
                    }
                }
            }
        }
    }


    @Override
    public boolean unLock(String key) {
        logger.debug("解锁{key: {}}", key);
        long current = System.currentTimeMillis();
        if (current < redisComponent.get(key)) {
            redisComponent.delete(key);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Long getLockTime(String key) {
        Long aLong = redisComponent.get(key);
        if(aLong == null){
            return null;
        }else{
            return System.currentTimeMillis() - aLong;
        }
    }

    private void sleep() {
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
