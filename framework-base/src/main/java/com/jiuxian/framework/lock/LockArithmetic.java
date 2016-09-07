package com.jiuxian.framework.lock;

public interface LockArithmetic {

    /**
     * 加锁算法
     * @param key
     * @return
     */
    boolean lock(String key, long timeOutMillisecond);

    /**
     * 加锁算法,默认超时时间为1分钟
     * @param key
     * @return
     */
    default boolean lock(String key){
        return lock(key, 1 * 60 * 1000L);
    }

    /**
     * 解锁算法
     * @param key
     * @return
     */
    boolean unLock(String key);

    /**
     * 已经获取锁时间
     * @param key
     * @return
     */
    Long getLockTime(String key);
}
