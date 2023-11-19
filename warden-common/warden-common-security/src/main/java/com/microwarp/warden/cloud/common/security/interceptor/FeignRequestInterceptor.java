package com.microwarp.warden.cloud.common.security.interceptor;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.security.TokenFactory;
import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * interceptor - feign 客户端请求拦截器
 * @author zhouwenqi
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Autowired
    @Qualifier( "aesTokenFactory")
    private TokenFactory tokenFactory;
    @Override
    public void apply(RequestTemplate template){
        try {
            TokenUser tokenUser = new TokenUser();
            tokenUser.setUserType(UserType.GUEST);
            tokenUser.setUserId("0");
            tokenUser.setUsername("figenUser");
            String feignToken = tokenFactory.generate(tokenUser);
            template.header(HttpConstant.HEAD_FEIGN_TOKEN_KEY, feignToken);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
