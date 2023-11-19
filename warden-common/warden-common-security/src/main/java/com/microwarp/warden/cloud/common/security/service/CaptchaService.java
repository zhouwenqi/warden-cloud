package com.microwarp.warden.cloud.common.security.service;

import com.microwarp.warden.cloud.common.core.cache.ICaptchaService;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.redis.constant.CacheKeys;
import com.microwarp.warden.cloud.common.security.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * service - captcha
 * @author zhouwenqi
 */
@Service
public class CaptchaService implements ICaptchaService {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private String getKey(String guestId){
        return CacheKeys.CAPTCHA_IMAGE_CODE_PREFIX + CacheKeys.KEY_SPLIT + guestId;
    }
    /**
     * 保存验证码信息
     * @param code 验证码
     */
    public void save(String code){
        String guestId = WebUtil.getGuestId();
        if(StringUtils.isBlank(guestId)){
            throw new WardenRequireParamterException("缺少Guest-Id参数");
        }
        String key = getKey(guestId);
        redisTemplate.opsForValue().set(key,code);
    }

    /**
     * 校验验证码信息
     * @param code 验证码
     * @return
     */
    public boolean verify(String code){
        String guestId = WebUtil.getGuestId();
        if(StringUtils.isBlank(guestId)){
            logger.warn("验证码校验失败：没有传Guest-Id");
            return false;
        }
        String key = getKey(guestId);
        Object codeObject = redisTemplate.opsForValue().get(key);
        return null == codeObject || codeObject.toString().toLowerCase().equals(code.toLowerCase());
    }
}
