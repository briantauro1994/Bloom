package com.covenant.app.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Development specific configuration - creates a localhost mysql datasource,
 * sets hibernate on create drop mode and inserts some test data on the
 * database.
 *
 * Set -Dspring.profiles.active=development to activate this config.
 *
 */
@Configuration
@Profile("development")
@EnableSpringDataWebSupport
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean(name = "datasource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/codeals");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource) {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPackagesToScan(new String[] { "com.covenant.app.model" });
		entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		String osName = System.getProperty("os.name");
		String userName = System.getProperty("user.name");
		Map<String, Object> jpaProperties = new HashMap<String, Object>();
		jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
		// jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		// jpaProperties.put("hibernate.hbm2ddl.auto", "create");
		jpaProperties.put("hibernate.show_sql", "true");
		jpaProperties.put("hibernate.format_sql", "true");
		jpaProperties.put("hibernate.use_sql_comments", "true");
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		jpaProperties.put("hibernate.search.default.directory_provide", "filesystem");
		if (osName.startsWith("W"))
			jpaProperties.put("hibernate.search.default.indexBase", "C:\\Index");
		else if (osName.startsWith("M"))
			jpaProperties.put("hibernate.search.default.indexBase", "/Users/" + userName + "/Documents/C/Index");
		jpaProperties.put("hibernate.hbm2ddl.import_files", "/import.sql");
		// jpaProperties.put("hibernate.id.new_generator_mappings", "true");
		entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

		return entityManagerFactoryBean;
	}

}
