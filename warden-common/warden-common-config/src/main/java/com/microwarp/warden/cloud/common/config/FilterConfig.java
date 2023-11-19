package com.microwarp.warden.cloud.common.config;

import com.microwarp.warden.cloud.common.config.filter.GuestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.Collections;

/**
 * config - filter
 */
@Configuration
public class FilterConfig {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return  new CorsFilter(source);
    }
    @Bean
    public FilterRegistrationBean guestFilterRegistrationBean(){
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GuestFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("guestFilter");
        registrationBean.setOrder(1);
        return  registrationBean;
    }
}
