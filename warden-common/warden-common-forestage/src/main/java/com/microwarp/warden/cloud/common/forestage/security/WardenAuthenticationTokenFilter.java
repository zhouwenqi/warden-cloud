package com.microwarp.warden.cloud.common.forestage.security;

import com.microwarp.warden.cloud.common.core.constant.AttrConstant;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.exception.WardenTokenErrorException;
import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.core.util.SpringContextUtil;
import com.microwarp.warden.cloud.common.security.service.RedisTokenService;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * filter - token 过滤器
 * 当前过滤器没有注入spring(刻意防止被托管)
 * @author zhouwenqi
 */
public class WardenAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(WardenAuthenticationTokenFilter.class);

    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    @Resource
    private RedisTokenService redisTokenService;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    public WardenAuthenticationTokenFilter(){
        super();
        this.handlerExceptionResolver = SpringContextUtil.getBean(HandlerExceptionResolverComposite.class);
        this.redisTokenService = SpringContextUtil.getBean(RedisTokenService.class);
        this.userDetailsService = SpringContextUtil.getBean(UserDetailsServiceImpl.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(HttpConstant.HEADER_TOKEN_KEY);
        if(StringUtils.isNotBlank(token)){
            TokenUser tokenUser = redisTokenService.parse(token);
            if(null == tokenUser){
                handlerExceptionResolver.resolveException(request,response,null,new WardenTokenErrorException());
                return;
            }

            // 判断token是否有效
            if(redisTokenService.isExpire(token)){
                logger.error("token效期已过，或已登出");
                handlerExceptionResolver.resolveException(request,response,null,new WardenTokenErrorException("token已失效"));
            }

            // 判断token是否是前台用户类型
            if(!tokenUser.getUserType().equals(UserType.NORMAL)){
                logger.error("非前台系统token:{}",token);
                handlerExceptionResolver.resolveException(request,response,null,new WardenTokenErrorException());
            }

            SecurityUser securityUser = userDetailsService.loadUserByUsername(tokenUser.getUsername());
            if(null == securityUser){
                handlerExceptionResolver.resolveException(request,response,null,new WardenTokenErrorException());
                return;
            }
            request.setAttribute(AttrConstant.SECURITY_USER_KEY, securityUser);
            request.setAttribute(AttrConstant.TOKEN_USER_KEY, tokenUser);
        }
        chain.doFilter(request,response);
    }
}
