package com.microwarp.warden.cloud.facade.user.domain.dto;

/**
 * dto - 更新密码
 */
public class UserPasswordDTO {
    /** 用户ID */
    private Long userId;
    /** 新的密码 */
    private String newPassowrd;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewPassowrd() {
        return newPassowrd;
    }

    public void setNewPassowrd(String newPassowrd) {
        this.newPassowrd = newPassowrd;
    }
}
