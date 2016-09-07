package com.jiuxian.framework.cache.impl;


import com.jiuxian.framework.cache.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


//@Component("redisCache")
public class RedisCacheServiceImpl implements CacheService{


    private RedisSerializer keySerializer = new StringRedisSerializer();
    private RedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
    @Value("${redis.namespace}")
    private String namespace;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public boolean set(String key, Object value, int expireSecond) {
        ShardedJedis resource = null;
        try{
            resource = shardedJedisPool.getResource();
            resource.set(keySerializer.serialize(getNamespaceKey(key)), valueSerializer.serialize(value));
            resource.expire(getNamespaceKey(key), expireSecond);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(resource !=null){
                resource.close();
            }
        }

    }

    @Override
    public Object get(String key) {
        ShardedJedis resource = null;
        try{
            resource = shardedJedisPool.getResource();
            byte[] bytes = resource.get(keySerializer.serialize(getNamespaceKey(key)));
            return valueSerializer.deserialize(bytes);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(resource !=null){
                resource.close();
            }
        }
    }

    @Override
    public Object delete(String key) {
        ShardedJedis resource = null;
        try{
            resource = shardedJedisPool.getResource();
            byte[] bytes = resource.get(keySerializer.serialize(getNamespaceKey(key)));
            Object obj = valueSerializer.deserialize(bytes);
            resource.del(getNamespaceKey(key));
            return obj;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(resource !=null){
                resource.close();
            }
        }
    }

    public String getString(String key){
        ShardedJedis resource = null;
        try{
            resource = shardedJedisPool.getResource();
            return resource.get(getNamespaceKey(key));
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(resource !=null){
                resource.close();
            }
        }
    }

    public boolean setString(String key, String value, int expireSecond){
        ShardedJedis resource = null;
        try{
            resource = shardedJedisPool.getResource();
            resource.set(getNamespaceKey(key), value);
            resource.expire(getNamespaceKey(key), expireSecond);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(resource !=null){
                resource.close();
            }
        }
    }

    public Long getLong(String key){
        String value = getString(key);
        if(StringUtils.isBlank(value)){
            return null;
        }else{
            return Long.valueOf(value);
        }
    }

    public boolean setLong(String key, Long value, int expireSecond){
        return setString(key, value.toString(), expireSecond);
    }

    public Integer getInt(String key){
        String value = getString(key);
        if(StringUtils.isBlank(value)){
            return null;
        }else{
            return Integer.valueOf(value);
        }
    }

    public boolean setInt(String key, Integer value, int expireSecond){
        return setString(key, value.toString(), expireSecond);
    }

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }
}
