package com.microwarp.warden.cloud.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration - 过滤器注入
 * @author zhouwenqi
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry){

    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
