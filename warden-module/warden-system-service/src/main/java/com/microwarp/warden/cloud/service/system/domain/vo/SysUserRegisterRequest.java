package com.microwarp.warden.cloud.service.system.domain.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * vo - 注册 - request
 */
public class SysUserRegisterRequest {
    /** 帐号 */
    @NotBlank(message = "帐号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,20}",message = "帐号只能是a-zA-Z0-9_-等6-20位字符")
    private String uid;
    /** 密码 */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{6,18}",message = "密码必需包含字母数字和特殊字符，长度为6-18")
    private String pwd;
    /** 电子邮箱 */
    @Email(message = "E-mail格式不正确，可以为空")
    @Length(min = 5, max=80,message = "E-mail只能是5-80个字符")
    private String email;
    /** 图形验证码 */
    private String captchaCode;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
