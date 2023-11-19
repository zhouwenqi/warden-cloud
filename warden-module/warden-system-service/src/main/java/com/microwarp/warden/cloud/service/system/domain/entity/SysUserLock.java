package com.microwarp.warden.cloud.service.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * entity - 系统用户锁
 * @author zhouwenqi
 */
@TableName("wd_sys_user_lock")
public class SysUserLock implements Serializable {
    private static final long serialVersionUID = -7295155541930627825L;
    /** 用户id */
    private Long userId;
    /** IP */
    private String ip;
    /** 锁定时间 */
    private Date lockTime;
    /** 解锁时间 */
    private Date unlockTime;

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

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(Date unlockTime) {
        this.unlockTime = unlockTime;
    }
}
