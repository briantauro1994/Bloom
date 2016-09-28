package com.covenant.app.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 *
 * The root context configuration of the application - the beans in this context will be globally visible
 * in all servlet contexts.
 *
 */

@Configuration
@ComponentScan({"com.covenant.app.services", "com.covenant.app.dao"})
public class RootContextConfig {

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory,
                                                         DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    
    }
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
     
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }    
}
  
