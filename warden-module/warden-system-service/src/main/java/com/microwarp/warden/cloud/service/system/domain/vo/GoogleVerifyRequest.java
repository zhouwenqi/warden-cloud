package com.microwarp.warden.cloud.service.system.domain.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * request - 二次验证请求
 * @author zhouwenqi
 */
public class GoogleVerifyRequest {
    /** 验证码 */
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^[0-9]{6}",message = "验证码只能是6位数字")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
