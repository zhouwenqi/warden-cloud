package com.microwarp.warden.cloud.common.security.interceptor;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.exception.WardenException;
import com.microwarp.warden.cloud.common.core.security.TokenFactory;
import com.microwarp.warden.cloud.common.core.security.WardenUser;
import com.microwarp.warden.cloud.common.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * interceptor - feign 生产端校验请求拦截器
 * @author zhouwenqi
 */
public class FeignApplyInterceptor implements HandlerInterceptor {
    @Autowired
    @Qualifier( "aesTokenFactory")
    private TokenFactory tokenFactory;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String feignToken = request.getHeader(HttpConstant.HEAD_FEIGN_TOKEN_KEY);
//        if(StringUtils.isBlank(feignToken)){
//            return true;
//        }
        try{
            WardenUser tokenUser = tokenFactory.parse(feignToken);
            System.out.println(JsonUtil.objectToJson(tokenUser));
        }catch (Exception e){
            throw new WardenException("Feign请求安全校验失败");
        }
        return true;
    }
}
