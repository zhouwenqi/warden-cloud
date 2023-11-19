package com.microwarp.warden.cloud.service.system.domain.vo;


/**
 * dto - 配置
 * @author zhouwenqi
 */
public class SysConfigVO {
    /** 开启注册功能 */
    private Boolean enabledRegister;
    /** 允许一个帐号生成多个有效token */
    private Boolean allowManyToken;

    public Boolean getEnabledRegister() {
        return enabledRegister;
    }

    public void setEnabledRegister(Boolean enabledRegister) {
        this.enabledRegister = enabledRegister;
    }

    public Boolean getAllowManyToken() {
        return allowManyToken;
    }

    public void setAllowManyToken(Boolean allowManyToken) {
        this.allowManyToken = allowManyToken;
    }
}
