package com.jiuxian.framework.cache;


import com.jiuxian.framework.spring.ApplicationContextHelper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


import java.lang.reflect.Method;

public class CacheInterceptor implements MethodInterceptor {

    private CacheService cacheService;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Cache cache = method.getAnnotation(Cache.class);
        if(cache != null){
            int expireSecond = cache.value();
            String cacheKey = invocation.getThis().getClass().getName() + "." + invocation.getMethod().getName() + "." + argsHashCode(invocation.getArguments());
            Object result = getCacheService().get(cacheKey);
            if (null != result) {
                return result;
            }
            result = invocation.proceed();
            getCacheService().set(cacheKey, result, expireSecond);
            return result;
        }
        return invocation.proceed();
    }

    private static int argsHashCode(Object[] arguments) {
        if (null == arguments || arguments.length == 0) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        for (Object object : arguments) {
            if (null != object) {
                sb.append(object.toString());
            }
            sb.append("|");
        }
        return sb.toString().hashCode();
    }

    private CacheService getCacheService() {
        if (null == cacheService) {
            cacheService = ApplicationContextHelper.getBean(CacheService.class);
        }
        return cacheService;
    }
}
