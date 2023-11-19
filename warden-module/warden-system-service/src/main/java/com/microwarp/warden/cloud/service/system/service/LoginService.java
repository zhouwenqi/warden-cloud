package com.microwarp.warden.cloud.service.system.service;

import com.microwarp.warden.cloud.common.core.enums.ActionStatusEnum;
import com.microwarp.warden.cloud.common.core.enums.AppTerminalEnum;
import com.microwarp.warden.cloud.common.core.enums.PlatformTypeEnum;
import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.core.util.AddressUtil;
import com.microwarp.warden.cloud.common.redis.constant.CacheKeys;
import com.microwarp.warden.cloud.common.security.service.RedisTokenService;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysLoginLogDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.service.system.config.WardenAdminConfig;
import com.microwarp.warden.cloud.service.system.constant.SecurityConstant;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * service -  登录
 * @author zhouwenqi.
 */
@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private SysUserLockService sysUserLockService;
    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private WardenAdminConfig wardenAdminConfig;
    @Autowired
    private RedisTokenService redisTokenService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private String getLoginFailedKey(Long userId, String ip){
        return CacheKeys.GUEST_ID_PREFIX + CacheKeys.KEY_SPLIT + userId + CacheKeys.KEY_SPLIT + ip;
    }

    /**
     * 获取登录失败缓存统计
     * @param userId 用户id
     * @param ip 登录ip
     * @return 失败次数
     */
    public long getLoginFailedCount(Long userId, String ip){
        String key = getLoginFailedKey(userId,ip);
        Long count = redisTemplate.opsForValue().increment(key,1);
        return null == count ? 0 : count;
    }

    /**
     * 缓存登录失败统计
     * @param userId 用户id
     * @param ip 登录ip
     */
    public void failed(Long userId,String ip){
        String key = getLoginFailedKey(userId,ip);
        redisTemplate.opsForValue().increment(key,1);
    }

    /**
     * 登录成功,并清除缓存失败统计
     * @param userId 用户id
     * @param ip 登录ip
     */
    public void success(Long userId,String ip){
        String key = getLoginFailedKey(userId,ip);
        redisTemplate.delete(key);
    }

    /**
     * 锁定用户(对应ip地址)
     * @param userId 用户id
     * @param ip ip地址
     */
    public void lock(Long userId,String ip){
        sysUserLockService.add(userId,ip,DateUtils.addMinutes(new Date(), SecurityConstant.LOGIN_LOCK_TIME));
        String key = getLoginFailedKey(userId,ip);
        redisTemplate.delete(key);
    }

    /**
     * 创建系统token凭据
     * @param sysUserDetailsDTO 用户详情
     * @return token凭据
     */
    public String createToken(SysUserDetailsDTO sysUserDetailsDTO){
        long effectiveHour = wardenAdminConfig.getEffectiveHour();
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserType(UserType.SYSTEM);
        tokenUser.setUsername(sysUserDetailsDTO.getUid());
        tokenUser.setUserId(sysUserDetailsDTO.getId().toString());
        return redisTokenService.create(tokenUser,effectiveHour);
    }

    /**
     * 写入登录日志
     * @param sysLoginLogDTO 日志信息
     */
    public void writeLog(SysLoginLogDTO sysLoginLogDTO){
        sysLoginLogService.add(sysLoginLogDTO);
    }

    /**
     * 写入登录日志(同步)
     * @param user 登录用户
     * @param logContent 日志内容
     * @param status 状态
     * @param ip IP地址
     * @param appTerminalType 应用类型
     * @param platformType 平台类型
     */
    public void writeLog(SysUserDetailsDTO user,String logContent, ActionStatusEnum status, String ip, AppTerminalEnum appTerminalType, PlatformTypeEnum platformType){
        if(null != user) {
            try {
                SysLoginLogDTO sysLoginLogDTO = new SysLoginLogDTO();
                String location = AddressUtil.getLocation(ip);
                sysLoginLogDTO.setUserId(user.getId());
                sysLoginLogDTO.setUid(user.getUid());
                sysLoginLogDTO.setAppTerminalType(appTerminalType);
                sysLoginLogDTO.setPlatformType(platformType);
                sysLoginLogDTO.setStatus(status);
                sysLoginLogDTO.setContent(logContent);
                sysLoginLogDTO.setLocation(location);
                sysLoginLogDTO.setIp(ip);
                writeLog(sysLoginLogDTO);
            }
            catch (Exception e){
                logger.warn("写入登录日志失败:{}",e.getMessage());
            }
        }
    }

    /**
     * 写入登录日志(异步)
     * @param user 登录用户
     * @param logContent 日志内容
     * @param status 状态
     * @param ip IP地址
     * @param appTerminalType 应用类型
     * @param platformType 平台类型
     */
    @Async("taskExecutor")
    public void asyncWriteLog(SysUserDetailsDTO user, String logContent, ActionStatusEnum status, String ip, AppTerminalEnum appTerminalType, PlatformTypeEnum platformType){
        writeLog(user,logContent,status,ip,appTerminalType,platformType);
    }
}
