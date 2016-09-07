package com.jiuxian.framework.db;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 取得数据源的KEY
 * 
 */
public class DataSourceRouter extends AbstractRoutingDataSource implements ApplicationContextAware {
	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(DataSourceRouter.class);
	// 数据源key的存储控制器
	@Autowired
	private DataSourceKeyManager dataSourceKeyManager;
	private ApplicationContext applicationContext;

	/**
	 * 获得数据源的key
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		try {
			return dataSourceKeyManager.getDataSourceKey();
		} catch (Throwable e) {
			throw new RuntimeException("get data source key fail", e);
		}
	}

	public DataSourceKeyManager getDataSourceKeyManager() {
		return dataSourceKeyManager;
	}

	public void setDataSourceKeyManager(DataSourceKeyManager dataSourceKeyManager) {
		this.dataSourceKeyManager = dataSourceKeyManager;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}