package com.microwarp.warden.cloud.common.core.security;

/**
 * service 缓存token服务
 * @author zhouwenqi
 */
public interface TokenService {
    /**
     * 创建Token
     * @param userType 用户类型
     * @param userId 用户ID
     * @param username 用户名
     * @param hours 有效时间(小时)
     * @return token凭据
     */
    String create(UserType userType, String userId, String username, Long hours);

    /**
     * 创建Token
     * @param wardenUser 用户信息
     * @param hours 有效时间(小时)
     * @return token凭据
     */
    String create(WardenUser wardenUser, Long hours);

    /**
     * 解密token信息
     * @param token token凭据
     * @return warden token 用户信息
     */
    WardenUser parse(String token);

    /**
     * 移除一个token
     * @param token token凭据
     */
    void remove(String token);

    /**
     * 重新设置token有效期
     * @param token token凭据
     * @param hours 有效时间(小时)
     */
    void expire(String token, Long hours);
    /**
     * 判断token是否失效
     * @param token Token凭据
     * @return
     */
    boolean isExpire(String token);
}
