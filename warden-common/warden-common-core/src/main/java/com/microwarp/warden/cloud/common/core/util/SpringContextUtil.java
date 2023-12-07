package com.microwarp.warden.cloud.common.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * util - SpringContext
 * @author zhouwenqi
 */
public class SpringContextUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static Boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static Boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return applicationContext.getType(name);
    }

}