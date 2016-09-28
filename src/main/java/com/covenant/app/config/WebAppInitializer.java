package com.covenant.app.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.covenant.app.config.root.CloudDatabaseConfig;
import com.covenant.app.config.root.DatabaseConfig;
import com.covenant.app.config.root.RootContextConfig;
import com.covenant.app.config.root.SecurityConfig;
import com.covenant.app.config.servlet.ServletContextConfig;

/**
 *
 * Replacement for most of the content of web.xml, sets up the root and the servlet context config.
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootContextConfig.class,CloudDatabaseConfig.class,SecurityConfig.class,DatabaseConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {ServletContextConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }




}


