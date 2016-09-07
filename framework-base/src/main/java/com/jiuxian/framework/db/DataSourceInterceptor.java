package com.jiuxian.framework.db;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 设置数据源KEY的拦截器
 */
public class DataSourceInterceptor implements MethodInterceptor {
    // 数据源key的存储控制器
    @Autowired
    private DataSourceKeyManager dataSourceKeyManager;

    @Autowired
    private TransactionInterceptor transactionInterceptor;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        List<Annotation> annotationList = new ArrayList<>();
        annotationList.addAll(Arrays.asList(method.getAnnotations()));
        annotationList.addAll(Arrays.asList(method.getDeclaringClass().getAnnotations()));
        String schema = "";
        for (Annotation annotation : annotationList) {
            if (annotation.annotationType().equals(Schema.class)) {
                Schema schemaClass = (Schema) annotation;
                schema = schemaClass.value();
                break;
            }
        }
        TransactionAttributeSource transactionAttributeSource = transactionInterceptor.getTransactionAttributeSource();
        TransactionAttribute transactionAttribute = transactionAttributeSource.getTransactionAttribute(method, method.getDeclaringClass());
        int propagationBehavior = transactionAttribute.getPropagationBehavior();
        boolean isMaster = false;
        if (readSet.contains(propagationBehavior)) {
            isMaster = false;
        }
        if (writeSet.contains(propagationBehavior)) {
            isMaster = true;
        }

        dataSourceKeyManager.setDataSourceKey(schema, isMaster);
        return invocation.proceed();
    }


    public static Set<Integer> writeSet = new HashSet<Integer>();
    public static Set<Integer> readSet = new HashSet<Integer>();

    static {
        writeSet.add(TransactionDefinition.PROPAGATION_REQUIRED);
        writeSet.add(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        writeSet.add(TransactionDefinition.PROPAGATION_MANDATORY);
        writeSet.add(TransactionDefinition.PROPAGATION_NESTED);
        readSet.add(TransactionDefinition.PROPAGATION_NEVER);
        readSet.add(TransactionDefinition.PROPAGATION_SUPPORTS);
        readSet.add(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
    }

}
