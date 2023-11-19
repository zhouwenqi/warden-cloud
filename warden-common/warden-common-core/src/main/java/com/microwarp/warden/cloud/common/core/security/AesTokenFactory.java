package com.microwarp.warden.cloud.common.core.security;

import com.microwarp.warden.cloud.common.core.util.AesUtil;
import com.microwarp.warden.cloud.common.core.util.TokenFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * aes - token生成工厂
 * @author zhouwenqi
 */
@Component("aesTokenFactory")
public class AesTokenFactory implements TokenFactory  {
    /**
     * 生成token
     * @param wardenUser 用户信息
     * @return token
     * @throws Exception
     */
    @Override
    public String generate(WardenUser wardenUser) throws Exception {
        String tokenString = TokenFactoryUtil.generate(wardenUser);
        return AesUtil.base64Encrypt(tokenString);
    }

    /**
     * 解码token信息
     * @param token token凭据
     * @return warden用户信息
     * @throws Exception
     */
    @Override
    public WardenUser parse(String token) throws Exception  {
        String tokenString = AesUtil.base64Decrypt(token);
        return TokenFactoryUtil.parseUser(tokenString);
    }

    /**
     * 获取token生成时间戳
     * @param token token凭据
     * @return 时间戳
     * @throws Exception
     */
    @Override
    public long getGenerateTime(String token) throws Exception {
        String tokenString = AesUtil.base64Decrypt(token);
        String[] tokenArray = TokenFactoryUtil.parseArray(tokenString);
        return Long.parseLong(tokenArray[3]);
    }
}
