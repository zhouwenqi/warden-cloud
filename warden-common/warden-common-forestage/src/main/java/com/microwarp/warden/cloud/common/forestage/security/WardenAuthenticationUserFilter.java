package com.microwarp.warden.cloud.common.forestage.security;

import com.microwarp.warden.cloud.common.core.constant.AttrConstant;
import com.microwarp.warden.cloud.common.core.exception.WardenAccountFailedException;
import com.microwarp.warden.cloud.common.core.util.SpringContextUtil;
import com.microwarp.warden.cloud.common.security.authenticator.WardenAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter - 解析(token)后的用户过淲器
 * 当前过滤器没有注入spring(刻意防止被托管)
 * @author zhouwenqi
 */
public class WardenAuthenticationUserFilter extends OncePerRequestFilter {
    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    public WardenAuthenticationUserFilter(){
        super();
        this.handlerExceptionResolver = SpringContextUtil.getBean(HandlerExceptionResolverComposite.class);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain chain) throws RuntimeException, ServletException, IOException{
        String uri = request.getRequestURI();
        SecurityUser securityUser = (SecurityUser)request.getAttribute(AttrConstant.SECURITY_USER_KEY);
        if(null != securityUser){
            if(!securityUser.isAccountNonLocked()){
                handlerExceptionResolver.resolveException(request,response,null,new WardenAccountFailedException("帐号已被锁住"));
                return;
            }
            if(!securityUser.isEnabled()){
                handlerExceptionResolver.resolveException(request,response,null,new WardenAccountFailedException("帐号已被禁用"));
                return;
            }
            WardenAuthentication wardenAuthentication = new WardenAuthentication(securityUser.getUser(),securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(wardenAuthentication);
        }
        chain.doFilter(request,response);
    }
}
