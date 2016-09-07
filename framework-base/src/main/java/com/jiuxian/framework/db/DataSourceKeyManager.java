package com.jiuxian.framework.db;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源key的存储控制器
 */
public class DataSourceKeyManager implements InitializingBean, ApplicationContextAware {
    private static final Logger log = Logger.getLogger(DataSourceKeyManager.class);
    // 数据源key的存储
    private static final ThreadLocal<String> DB_KEY = new ThreadLocal<String>();


    public Map<String, DataSourceKey> dataSourceKeyMap = new HashedMap();

    private ApplicationContext applicationContext;

    private String defaultSchema;


    public void setDataSourceKey(String schema, boolean isMaster){
        if(StringUtils.isBlank(schema)){
            schema = defaultSchema;
        }
        DataSourceKey dataSourceKey = dataSourceKeyMap.get(schema);
        if(dataSourceKey == null){
            throw new RuntimeException("schema error, please check database config in application.xml or check annotation in dao!");
        }
        if(isMaster){
            DB_KEY.set(dataSourceKey.getMaster());
        }else{
            DB_KEY.set(dataSourceKey.getRandomSlave());
        }
    }

    public Map<String, DataSourceKey> getDataSourceKeyMap() {
        return dataSourceKeyMap;
    }

    public void setDataSourceKeyMap(Map<String, DataSourceKey> dataSourceKeyMap) {
        this.dataSourceKeyMap = dataSourceKeyMap;
    }

    public String getDataSourceKey(){
        return DB_KEY.get();
    }


    public String getDefaultSchema() {
        return defaultSchema;
    }

    public void setDefaultSchema(String defaultSchema) {
        this.defaultSchema = defaultSchema;
    }

    public void afterPropertiesSet() throws Exception {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
