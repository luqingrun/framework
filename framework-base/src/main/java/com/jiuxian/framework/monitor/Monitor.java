package com.jiuxian.framework.monitor;

public class Monitor implements java.io.Serializable {

    private static final long serialVersionUID = 4150169011880225013L;
    /**
     * 类型:目前支持2种:http client,spring aop
     */
    private String type;

    /**
     * 执行名称
     */
    private String name;

    /**
     * 执行线程ID
     */
    private String threadId;

    /**
     * 成功数量
     */
    private long success;

    /**
     * 失败数量
     */
    private long failure;

    /**
     * 执行时间
     */
    private long elapsed;

    /**
     * 并发执行数量
     */
    private long concurrent;

    /**
     * 最长执行时间
     */
    private long maxElapsed;

    /**
     * 最大并发数
     */
    private long maxConcurrent;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public long getFailure() {
        return failure;
    }

    public void setFailure(long failure) {
        this.failure = failure;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    public long getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(long concurrent) {
        this.concurrent = concurrent;
    }

    public long getMaxElapsed() {
        return maxElapsed;
    }

    public void setMaxElapsed(long maxElapsed) {
        this.maxElapsed = maxElapsed;
    }

    public long getMaxConcurrent() {
        return maxConcurrent;
    }

    public void setMaxConcurrent(long maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }
}
