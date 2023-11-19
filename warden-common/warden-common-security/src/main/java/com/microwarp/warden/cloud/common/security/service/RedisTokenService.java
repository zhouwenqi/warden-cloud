package com.microwarp.warden.cloud.common.security.service;

import com.microwarp.warden.cloud.common.core.security.CacheTokenService;
import com.microwarp.warden.cloud.common.core.security.TokenFactory;
import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.core.security.WardenUser;
import com.microwarp.warden.cloud.common.redis.constant.CacheKeys;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * service - 缓存token
 * @author zhouwenqi
 */
@Component
public class RedisTokenService implements CacheTokenService {
    private static final Logger logger = LoggerFactory.getLogger(RedisTokenService.class);
    // 默认采用rsa工厂生成token
    @Autowired
    @Qualifier( "rsaTokenFactory")
    private TokenFactory tokenFactory;

    @Autowired
    private RedisTemplate redisTemplate;

    public String getTokenKey(String token){
        return CacheKeys.TOKEN_PREFIX + CacheKeys.KEY_SPLIT + token;
    }

    public String getTokensKey(TokenUser tokenUser){
        return CacheKeys.ONLINE_PREFIX + CacheKeys.KEY_SPLIT + tokenUser.getUserType().name()+CacheKeys.KEY_SPLIT+tokenUser.getUserId();
    }


    /**
     * 创建Token
     * @param userType 用户类型
     * @param userId 用户ID
     * @param username 用户名
     * @param hours 有效时间(小时)
     * @return token凭据
     */
    @Override
    public String create(UserType userType, String userId, String username, Long hours){
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserId(userId);
        tokenUser.setUsername(username);
        tokenUser.setUserType(userType);
       return create(tokenUser,hours);
    }

    /**
     * 创建Token
     * @param wardenUser 用户信息
     * @param hours 有效时间(小时)
     * @return token凭据
     */
    @Override
    public  String create(WardenUser wardenUser, Long hours){
        String token = null;
        try {
            token = tokenFactory.generate(wardenUser);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(StringUtils.isNotBlank(token)){
            String key = getTokenKey(token);
            redisTemplate.opsForValue().set(key,token, hours, TimeUnit.HOURS);
        }
        return token;
    }

    /**
     * 解密token信息
     * @param token token凭据
     * @return warden token 用户信息
     */
    @Override
    public TokenUser parse(String token){
        TokenUser tokenUser = null;
        try {
            WardenUser wardenUser = tokenFactory.parse(token);
            tokenUser = new TokenUser();
            BeanUtils.copyProperties(wardenUser,tokenUser);
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("token decrypt failed:{}",token);
        }
        return tokenUser;
    }

    /**
     * 移除一个token
     * @param token token凭据
     */
    @Override
    public void remove(String token){
        if(StringUtils.isBlank(token)){
            return;
        }
        String key = getTokenKey(token);
        redisTemplate.delete(key);
    }

    /**
     * 重新设置token有效期
     * @param token token凭据
     * @param hours 有效时间(小时)
     */
    @Override
    public void expire(String token, Long hours){
        redisTemplate.expire(token,hours,TimeUnit.HOURS);
    }

    /**
     * 判断token是否失效
     * @param token token凭据
     * @return
     */
    @Override
    public boolean isExpire(String token){
        if(StringUtils.isNotBlank(token)){
            String key = getTokenKey(token);
            if(!redisTemplate.hasKey(key)){
                logger.warn("token：{} 已不存缓存中",token);
                return true;
            }
            return redisTemplate.getExpire(key) < -1;
        }
        return true;
    }

    /**
     * 获取一个用户已登录token列表
     * @param tokenUser 用户信息
     * @return
     */
    public List<String> getLoggedTokens(TokenUser tokenUser){
        String key = getTokensKey(tokenUser);
        SetOperations<String,String> redisOperations = redisTemplate.opsForSet();
        Set<String> set = redisOperations.members(key);
        return null == set ? new ArrayList<>() : new ArrayList<>(set);
    }

    /**
     * 加入用户登录token列表
     * @param tokenUser 用户信息
     * @param token token凭据
     */
    public void addLoggedToken(TokenUser tokenUser, String token){
        String key = getTokensKey(tokenUser);
        SetOperations<String,String> redisOperations = redisTemplate.opsForSet();
        redisOperations.add(key,token);
    }

    /**
     * 移除用户token信息
     * @param tokenUser 用户信息
     * @param token token凭据
     */
    public void removeLoggedToken(TokenUser tokenUser, String token){
        String key = getTokensKey(tokenUser);
        SetOperations<String,String> redisOperations = redisTemplate.opsForSet();
        redisOperations.remove(key,token);
    }

    /**
     * 移除用户token信息
     * @param token token凭据
     */
    public void removeLoggedToken(String token){
        TokenUser tokenUser = parse(token);
        if(null == token){
            return;
        }
        String key = getTokensKey(tokenUser);
        SetOperations<String,String> redisOperations = redisTemplate.opsForSet();
        redisOperations.remove(key,token);
    }

    /**
     * 清除一个用户的所有已登录token
     * @param tokenUser 用户信息
     */
    public void clearLoggedToken(TokenUser tokenUser){
        String key = getTokensKey(tokenUser);
        redisTemplate.delete(key);
    }

    /**
     * 判断token是否已登录在列表中
     * @param tokenUser 用户信息
     * @param token token凭据
     * @return
     */
    public Boolean hasLogged(TokenUser tokenUser, String token){
        String key = getTokensKey(tokenUser);
        if(StringUtils.isBlank(key) || StringUtils.isBlank(token)){
            return false;
        }
        return redisTemplate.opsForSet().isMember(key,token);
    }

    /**
     * 判断token是否已登录在列表中
     * @param token token凭据
     * @return
     */
    public Boolean hasLogged(String token){
        TokenUser tokenUser = parse(token);
        if(null == token){
            return false;
        }
        String key = getTokensKey(tokenUser);
        if(StringUtils.isBlank(key) || StringUtils.isBlank(token)){
            return false;
        }
        return redisTemplate.opsForSet().isMember(key,token);
    }
}
