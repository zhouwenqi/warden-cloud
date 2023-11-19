package com.microwarp.warden.cloud.common.core.cache;

/**
 * service - 验证码
 */
public interface ICaptchaService {
    void save(String code);
    boolean verify(String code);
}
