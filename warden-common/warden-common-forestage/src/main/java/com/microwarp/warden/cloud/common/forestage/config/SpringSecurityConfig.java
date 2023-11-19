package com.microwarp.warden.cloud.common.forestage.config;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.forestage.security.UserDetailsServiceImpl;
import com.microwarp.warden.cloud.common.forestage.security.WardenAuthenticationTokenFilter;
import com.microwarp.warden.cloud.common.forestage.security.WardenAuthenticationUserFilter;
import com.microwarp.warden.cloud.common.security.authenticator.WardenAccessDeninedHandler;
import com.microwarp.warden.cloud.common.security.authenticator.WardenAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration - Security 装配
 * @author zhouwenqi
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/product/**").permitAll()
                .antMatchers("/order","/order/**","/payment","/payment","/product","/product/**").authenticated()
                .anyRequest().permitAll();
//                .anyRequest().authenticated();
        httpSecurity.logout().logoutUrl("/logout");
        httpSecurity.headers().cacheControl();
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(new WardenAuthenticationEntryPoint())
                .accessDeniedHandler(new WardenAccessDeninedHandler()).and()
                .addFilterBefore(new WardenAuthenticationUserFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new WardenAuthenticationTokenFilter(), WardenAuthenticationUserFilter.class);
    }

    /**
     * 排除完全不需要检查和解析 token 凭据的路由
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS)
                .antMatchers(HttpConstant.FEIGN_URI_PREFIX+"/**")
                .antMatchers("/login")
                .antMatchers("/register")
                .antMatchers("/error")
                .antMatchers("/captcha/**")
                .antMatchers("/config/global")
                .antMatchers("/test/**")
                .antMatchers("/*.ico");
        super.configure(web);
    }
}
