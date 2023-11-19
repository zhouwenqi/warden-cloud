package com.microwarp.warden.cloud.service.system.config;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.security.authenticator.WardenAccessDeninedHandler;
import com.microwarp.warden.cloud.common.security.authenticator.WardenAuthenticationEntryPoint;
import com.microwarp.warden.cloud.service.system.security.WardenAuthenticationTokenFilter;
import com.microwarp.warden.cloud.service.system.security.WardenAuthenticationUserFilter;
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
 * configuration - security
 * @author zhouwenqi
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/logout").permitAll()
                .anyRequest().authenticated();
        httpSecurity.logout().logoutUrl("/login");
        httpSecurity.headers().cacheControl();
        httpSecurity.headers().cacheControl();
        httpSecurity.exceptionHandling().authenticationEntryPoint(new WardenAuthenticationEntryPoint())
                .accessDeniedHandler(new WardenAccessDeninedHandler()).and()
                .addFilterBefore(new WardenAuthenticationUserFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new WardenAuthenticationTokenFilter(), WardenAuthenticationUserFilter.class);
    }

    /**
     * 排除完全不需要检查和解析token凭据的路由
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
