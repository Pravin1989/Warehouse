package com.amiablecore.warehouse.config;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Pravin
 *
 */
@Configuration
@EnableTransactionManagement
public class PersistenceJdbcConfig {

	/**
	 * @return
	 */
	@ConfigurationProperties(prefix = "db100.datasource")
	@Bean
	public DataSource db101DataSource() {
		PoolConfiguration pool = new DataSource();
		pool.setUrl("jdbc:sqlserver://localhost;databaseName=testdb");
		pool.setUsername("sa");
		pool.setPassword("");
		pool.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return new DataSource(pool);
	}

	/**
	 * @return
	 */
	@Bean
	public DataSourceTransactionManager getDb101TransactionManager() {
		DataSourceTransactionManager db101SourceTransactionManager = new DataSourceTransactionManager();
		db101SourceTransactionManager.setDataSource(db101DataSource());
		return db101SourceTransactionManager;
	}

	/**
	 * @return
	 */
	@Bean
	public JdbcTemplate getDB101JdbcTemplate() {
		return new JdbcTemplate(db101DataSource());
	}

}
