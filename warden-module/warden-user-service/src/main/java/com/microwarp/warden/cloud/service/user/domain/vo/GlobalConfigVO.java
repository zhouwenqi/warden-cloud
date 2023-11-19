package com.microwarp.warden.cloud.service.user.domain.vo;


import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.enums.CaptchaTypeEnum;

/**
 * vo - 全局配置信息 - response
 */
public class GlobalConfigVO {
    /** token有效时间(单位:小时) */
    private long tokenEffectiveHour  = 24 * 7;
    /** 默认分页大小 */
    private int defaultPageSize = HttpConstant.DEFAULT_PAGE_SIZE;
    /** 来宾帐号 */
    private String guestId;
    /** 启用验证码 */
    private Boolean enableCaptcha;
    /** 验证码类型 */
    private CaptchaTypeEnum captchaType;

    public long getTokenEffectiveHour() {
        return tokenEffectiveHour;
    }

    public void setTokenEffectiveHour(long tokenEffectiveHour) {
        this.tokenEffectiveHour = tokenEffectiveHour;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public Boolean getEnableCaptcha() {
        return enableCaptcha;
    }

    public void setEnableCaptcha(Boolean enableCaptcha) {
        this.enableCaptcha = enableCaptcha;
    }

    public CaptchaTypeEnum getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(CaptchaTypeEnum captchaType) {
        this.captchaType = captchaType;
    }
}
