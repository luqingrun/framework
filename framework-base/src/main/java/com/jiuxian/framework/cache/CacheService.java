package com.jiuxian.framework.cache;

public interface CacheService{

    static int DEFAULT_EXPIRE_SECOND = 24* 60 * 60;

    String getNamespace();

    default String getNamespaceKey(String key) {
        if(getNamespace() == null || getNamespace().trim().length()==0){
            return key;
        }else{
            return getNamespace().trim()+":"+key;
        }
    }

    default boolean set(String key, Object value) {
        return set(key, value , DEFAULT_EXPIRE_SECOND);
    }

    boolean set(String key, Object value, int expireSecond);

    default <T> T get(String key, Class<T> clazz) {
        return (T) get(key);
    }

    Object get(String key);

    Object delete(String key);

    default <T> T delete(String key, Class<T> clazz) {
        return (T) delete(key);
    }

    default Long getLong(String key){
        return get(key, Long.class);
    }

    default boolean setLong(String key, Long value, int expireSecond){
        return set(key, value, expireSecond);
    }

    default boolean setLong(String key, Long value){
        return setLong(key, value, DEFAULT_EXPIRE_SECOND);
    }

    default String getString(String key){
        return get(key, String.class);
    }

    default boolean setString(String key, String value, int expireSecond){
        return set(key, value, expireSecond);
    }

    default boolean setString(String key, String value){
        return setString(key, value, DEFAULT_EXPIRE_SECOND);
    }

    default Integer getInt(String key){
        return get(key, Integer.class);
    }

    default boolean setInt(String key, Integer value, int expireSecond){
        return set(key, value, expireSecond);
    }

    default boolean setInt(String key, Integer value){
        return setInt(key, value, DEFAULT_EXPIRE_SECOND);
    }
}
