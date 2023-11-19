package com.microwarp.warden.cloud.service.system.domain.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * vo - 更新系统用户密码 - request
 */
public class SysUserPasswordRequest {
    /** 用户ID */
    @NotNull(message = "帐号ID不能为空")
    private Long userId;
    /** 新的密码 */
    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{6,18}",message = "密码必需包含字母数字和特殊字符，长度为6-18")
    private String newPassword;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
