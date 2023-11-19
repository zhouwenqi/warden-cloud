package com.microwarp.warden.cloud.common.core.security;

/**
 * warden user
 * @author zhouwenqi
 */
public interface WardenUser {
    /** 获取用户ID */
    String getUserId();
    /** 获取用户名(帐号) */
    String getUsername();
    /** 获取用户类型 */
    UserType getUserType();
}
