package com.microwarp.warden.cloud.common.core.util;

import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.core.security.WardenUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Token - util
 * @author zhouwenqi
 */
public class TokenFactoryUtil {
    private static final Logger logger = LoggerFactory.getLogger(TokenFactoryUtil.class);
    public static String generate(WardenUser wardenUser){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(wardenUser.getUserType().name());
        stringBuilder.append(":");
        stringBuilder.append(wardenUser.getUserId());
        stringBuilder.append(":");
        stringBuilder.append(wardenUser.getUsername());
        stringBuilder.append(":");
        stringBuilder.append(System.currentTimeMillis());
        return stringBuilder.toString();
    }

    public static String[] parseArray(String tokenString) throws Exception {
        String[] tokens = tokenString.split(":");
        if(tokens.length < 4){
            throw new IOException("token format error...");
        }
        return tokens;
    }

    public static WardenUser parseUser(String[] tokenArray){
        return new WardenUser() {
            @Override
            public String getUserId() {
                return tokenArray[1];
            }

            @Override
            public String getUsername() {
                return tokenArray[2];
            }

            @Override
            public UserType getUserType() {
                return UserType.valueOf(tokenArray[0]);
            }
        };
    }

    public static WardenUser parseUser(String tokenString) throws Exception {
        String[] tokens = parseArray(tokenString);
        return parseUser(tokens);
    }
}
