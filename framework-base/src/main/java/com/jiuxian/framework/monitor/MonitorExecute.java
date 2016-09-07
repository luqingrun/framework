package com.jiuxian.framework.monitor;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import com.jiuxian.framework.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MonitorExecute {

    private static final ThreadLocal<String> thread_uuid = new ThreadLocal<>();

    private static Log log = LogFactory.getLog(MonitorExecute.class);
    public static ConcurrentMap<String, AtomicReference<Monitor>> statisticsMap = new ConcurrentHashMap<>();
    // 定时任务执行器
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3, new NamedThreadFactory("MonitorSendTimer", true));
    // 统计信息收集定时器
    private static ScheduledFuture<?> sendFuture = null;
    private static final long monitorInterval = 60000L;

    private static ConcurrentMap<String, AtomicInteger> concurrents = new ConcurrentHashMap<String, AtomicInteger>();

    // 获取并发计数器
    public static AtomicInteger getConcurrent(String url) {
        String key = url;
        AtomicInteger concurrent = concurrents.get(key);
        if (concurrent == null) {
            concurrents.putIfAbsent(key, new AtomicInteger());
            concurrent = concurrents.get(key);
        }
        return concurrent;
    }

    public static String getThreadUUID(){
        String uuid = thread_uuid.get();
        if(StringUtils.isBlank(uuid)){
            uuid = UUID.randomUUID().toString();
            thread_uuid.set(uuid);
        }
        return uuid;
    }


    static{
        log.info("monitor run");
        // 启动统计信息收集定时器
        sendFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            // 收集统计信息
            try {
                send();
            } catch (Throwable t) { // 防御性容错
                log.error("Unexpected error occur at send statistic, cause: " + t.getMessage(), t);
            }
        }, monitorInterval, monitorInterval, TimeUnit.MILLISECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                try {
                    send();
                } catch (Throwable t) { // 防御性容错
                    log.error("Unexpected error occur at send statistic, cause: " + t.getMessage(), t);
                }
            }
        });
    }

    public static void send() {
        Set<String> uriSet = statisticsMap.keySet();
        for (String uri : uriSet) {
            AtomicReference<Monitor> reference = statisticsMap.remove(uri);
            Monitor monitor = reference.get();
            log.info("monitor json="+ JsonUtils.objectToJson(monitor));
        }
    }

    public static void collect(String name, String type, long elapsed, boolean error) {
        int concurrent = MonitorExecute.getConcurrent(name).get(); // 当前并发数
        AtomicReference<Monitor> reference = MonitorExecute.statisticsMap.get(name);
        if (reference == null) {
            MonitorExecute.statisticsMap.putIfAbsent(name, new AtomicReference<Monitor>());
            reference = MonitorExecute.statisticsMap.get(name);
        }
        int success = 0;
        int failure = 0;
        if(error){
            failure = 1;
        }else {
            success = 1;
        }
        // CompareAndSet并发加入统计数据
        Monitor current;
        Monitor update = new Monitor();
        update.setThreadId(MonitorExecute.getThreadUUID());
        update.setType(type);
        update.setName(name);
        do {
            current = reference.get();
            if (current == null) {
                update.setSuccess(success);
                update.setFailure(failure);
                update.setElapsed(elapsed);
                update.setConcurrent(concurrent);
                update.setMaxElapsed(elapsed);
                update.setMaxConcurrent(concurrent);
            } else {
                update.setSuccess(current.getSuccess()+success);
                update.setFailure(current.getFailure() + failure);
                update.setElapsed(current.getElapsed() + elapsed);
                update.setConcurrent((current.getConcurrent() + concurrent)/2);
                update.setMaxElapsed(current.getMaxElapsed() > elapsed ? current.getMaxElapsed() : elapsed);
                update.setMaxConcurrent(current.getMaxConcurrent() > concurrent ? current.getMaxConcurrent() : concurrent);
            }
        } while (! reference.compareAndSet(current, update));
    }
}
