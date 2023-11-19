package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.service.system.dao.SysUserLockDao;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUserLock;
import com.microwarp.warden.cloud.service.system.service.SysUserLockService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * service - 系统用户锁 - impl
 * @author zhouwenqi
 */
@Service
public class SysUserLockServiceImpl extends BaseServiceImpl<SysUserLock,SysUserLockDao> implements SysUserLockService {
    /**
     * 判断用户是否被锁定
     * @param userId 用户id
     * @param ip ip地址
     * @return
     */
    @Override
    public boolean isLocked(Long userId,String ip){
        return this.dao.isLocked(userId,ip);
    }

    /**
     * 统计当前ip锁记录数
     * @param ip ip地址
     * @return
     */
    @Override
    public long totalByIp(String ip){
        QueryWrapper<SysUserLock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip",ip);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        queryWrapper.ge("unlock_time", simpleDateFormat.format(new Date()));
        return this.dao.count(queryWrapper);
    }

    /**
     * 添加一条锁记录
     * @param userId 用户id
     * @param ip IP地址
     * @param unlockTime 解锁时间(时间为空永久锁住)
     */
    @Override
    public void add(Long userId, String ip, Date unlockTime){
        this.dao.lock(userId,ip,unlockTime);
    }
}
