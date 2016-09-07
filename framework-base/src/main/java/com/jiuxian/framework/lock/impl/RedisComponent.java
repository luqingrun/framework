package com.jiuxian.framework.lock.impl;

import com.jiuxian.framework.cache.CacheService;
import com.jiuxian.framework.cache.impl.RedisCacheServiceImpl;
import com.jiuxian.framework.spring.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by luqingrun on 16/8/19.
 */
@Component("redisComponent")
public class RedisComponent {

    private RedisCacheServiceImpl redisCacheService;

    private String LOCK = "lock";

    private String getLockKey(String key){
        return getCacheService().getNamespaceKey(key) + "_LOCK";
    }

    public Boolean setIfAbsent(String key, Long timeMillisecond) {
        ShardedJedis shardedJedis = getCacheService().getShardedJedisPool().getResource();
        try{
            Long setnx = shardedJedis.setnx(getLockKey(key), String.valueOf(timeMillisecond));
            return JedisConverters.toBoolean(setnx);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(shardedJedis !=null) {
                shardedJedis.close();
            }
        }
    }

    public Long get(String key) {
        ShardedJedis shardedJedis = getCacheService().getShardedJedisPool().getResource();
        try{
            String timeMillisecond = shardedJedis.get(getLockKey(key));
            if(timeMillisecond != null){
                return Long.valueOf(timeMillisecond);
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(shardedJedis !=null) {
                shardedJedis.close();
            }
        }

    }

    public Long getAndSet(String key, Long timeMillisecond) {
        ShardedJedis shardedJedis = getCacheService().getShardedJedisPool().getResource();
        try{
            String set = shardedJedis.getSet(getLockKey(key), String.valueOf(timeMillisecond));
            if(set != null){
                return Long.valueOf(set);
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(shardedJedis !=null) {
                shardedJedis.close();
            }
        }
    }

    public void delete(String key) {
        ShardedJedis shardedJedis = getCacheService().getShardedJedisPool().getResource();
        try{
            shardedJedis.del(getLockKey(key));
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(shardedJedis !=null) {
                shardedJedis.close();
            }
        }
    }

    private RedisCacheServiceImpl getCacheService() {
        if (null == redisCacheService) {
            redisCacheService = ApplicationContextHelper.getBean(RedisCacheServiceImpl.class);
        }
        return redisCacheService;
    }

}
