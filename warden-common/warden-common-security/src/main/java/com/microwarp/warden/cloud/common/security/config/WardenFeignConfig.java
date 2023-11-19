package com.microwarp.warden.cloud.common.security.config;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.exception.FeignExceptionDecoder;
import com.microwarp.warden.cloud.common.security.interceptor.FeignApplyInterceptor;
import com.microwarp.warden.cloud.common.security.interceptor.FeignRequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * configuration - feign
 */
@Configuration
public class WardenFeignConfig  implements WebMvcConfigurer {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new FeignExceptionDecoder();
    }
    @Bean
    public FeignRequestInterceptor feignRequestInterceptor(){
        return new FeignRequestInterceptor();
    }
    @Bean
    public FeignApplyInterceptor feignApplyInterceptor(){
        return new FeignApplyInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(feignApplyInterceptor()).addPathPatterns(HttpConstant.FEIGN_URI_PREFIX+"/**");
    }
}
