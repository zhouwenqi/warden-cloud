package com.microwarp.warden.cloud.service.user.config;

import com.microwarp.warden.cloud.common.core.enums.CaptchaTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * configuration - 用户配置
 * @author zhouwenqi
 */
@ConfigurationProperties(prefix = "warden.user")
@Configuration
public class WardenUserConfig {
    /** 启用验证码 */
    private Boolean enableCaptcha = false;
    /** 验证码类型 */
    private CaptchaTypeEnum captchaType = CaptchaTypeEnum.KAPTCHA_IMAGE;

    private long effectiveHour = 7 * 24;

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

    public long getEffectiveHour() {
        return effectiveHour;
    }

    public void setEffectiveHour(long effectiveHour) {
        this.effectiveHour = effectiveHour;
    }
}
