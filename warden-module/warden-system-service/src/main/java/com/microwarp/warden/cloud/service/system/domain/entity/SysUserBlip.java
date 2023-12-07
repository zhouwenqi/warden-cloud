package com.microwarp.warden.cloud.service.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * entity - 用户标记(需要二次验证)
 * @author zhouwenqi
 */
@TableName("wd_sys_user_blip")
public class SysUserBlip implements Serializable {
    private static final long serialVersionUID = -7800463211238620054L;
    /** 用户id */
    private Long userId;
    /** ip地址 */
    private String ip;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
