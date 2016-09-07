package com.jiuxian.framework.monitor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AopInterceptor implements MethodInterceptor {
    private static Log log = LogFactory.getLog(AopInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis(); // 开始时间
        String className = invocation.getThis().getClass().getName() + "." + invocation.getMethod().getName();
        MonitorExecute.getConcurrent(className).incrementAndGet();
        boolean error = false;
        try {
            Object result = invocation.proceed();
            error =false;
            return result;
        }catch (Exception e){
            error =true;
            throw e;
        }finally {
            long elapsed = System.currentTimeMillis() - start; // 计算调用耗时
            MonitorExecute.collect(className, "aop", elapsed, error);
            MonitorExecute.getConcurrent(className).decrementAndGet();
        }
    }

}
