package com.microwarp.warden.cloud.common.security.token;

import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.core.security.WardenUser;

/**
 * token user
 */
public class TokenUser implements WardenUser {
    /** 用户ID */
    private String userId;
    /** 用户名 */
    private String username;
    /** 用户类型 */
    private UserType userType;

    @Override
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
