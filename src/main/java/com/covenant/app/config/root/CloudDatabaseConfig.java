package com.covenant.app.config.root;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.covenant.app.model.CDCustomNamingStrategy;

@Configuration
@Profile("cloud")
@EnableTransactionManagement
public class CloudDatabaseConfig  {
//	@Bean
//    public DataSource inventoryDataSource() {
//        return connectionFactory().dataSource("mysql");
//    }
//	 @Bean
//	    public DataSource gdataSource() {
//	        CloudFactory cloudFactory = new CloudFactory();
//	        Cloud cloud = cloudFactory.getCloud();
//	        String serviceID = cloud.getServiceID();
//	        return cloud.getServiceConnector(serviceID, DataSource.class, null);
//	    }
	    @Bean(name = "namingStrategy")
	    public ImprovedNamingStrategy getNamingStrategy(){
	    	
	    	ImprovedNamingStrategy namingStrategy = new  CDCustomNamingStrategy();
	    	return namingStrategy;
	    }
	    @Bean(name="dataSource")
	    public BasicDataSource dataSource() throws PropertyVetoException {
	        BasicDataSource bean = new BasicDataSource();
	        bean.setDriverClassName("com.mysql.jdbc.Driver");
	        bean.setUrl("jdbc:mysql://localhost:3306/bluemix?useUnicode=true&characterEncoding=UTF-8");
	        bean.setUsername("root");
	        bean.setPassword("root");
	        return bean;
	    }
	    @Bean(name = "entityManagerFactory")
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, ImprovedNamingStrategy ins) {

	        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	        entityManagerFactoryBean.setDataSource(dataSource);
	        entityManagerFactoryBean.setPackagesToScan(new String[]{"com.covenant.app.model"});
	        entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
	        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	      

	        Map<String, Object> jpaProperties = new HashMap<String, Object>();
	        jpaProperties.put("database", "mysql");
	        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
	        jpaProperties.put("hibernate.show_sql", "true");
	        jpaProperties.put("hibernate.format_sql", "true");
	        jpaProperties.put("hibernate.use_sql_comments", "true");
	        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	        jpaProperties.put("hibernate.ejb.naming_strategy", ins);
	        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

	        return entityManagerFactoryBean;
	    }
	
	}

