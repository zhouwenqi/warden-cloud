package com.microwarp.warden.cloud.service.system.security;

import com.microwarp.warden.cloud.common.core.constant.AttrConstant;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.exception.WardenTokenErrorException;
import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.core.util.SpringContextUtil;
import com.microwarp.warden.cloud.common.security.service.RedisTokenService;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.service.system.service.SysConfigService;
import com.microwarp.warden.cloud.service.system.service.impl.SysConfigServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author zhouwenqi
 */
public class WardenAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(WardenAuthenticationTokenFilter.class);

    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private SysUserDetailsService sysUserDetailsService;

    @Resource
    private RedisTokenService redisTokenService;

    public WardenAuthenticationTokenFilter(){
        super();
        this.handlerExceptionResolver = SpringContextUtil.getBean(HandlerExceptionResolverComposite.class);
        this.sysConfigService = SpringContextUtil.getBean(SysConfigServiceImpl.class);
        this.sysUserDetailsService = SpringContextUtil.getBean(SysUserDetailsService.class);
        this.redisTokenService = SpringContextUtil.getBean(RedisTokenService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(HttpConstant.HEADER_TOKEN_KEY);
        String uri = request.getRequestURI();
        if(StringUtils.isNotBlank(token)){
            // 解码token信息
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

            // 判断token是否是后台系统用户类型
            if(!tokenUser.getUserType().equals(UserType.SYSTEM)){
                logger.error("非后台系统token:{}",token);
                handlerExceptionResolver.resolveException(request,response,null,new WardenTokenErrorException());
            }

            // 判断是否只允许保持一个token会话
            if(!sysConfigService.findCurrent().getAllowManyToken() && !redisTokenService.hasLogged(tokenUser,token)){
                logger.error("未登录的token:{}",token);
                handlerExceptionResolver.resolveException(request,response,null,new WardenTokenErrorException());
            }

            SecurityUser securityUser = sysUserDetailsService.loadUserByUsername(tokenUser.getUsername());
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
