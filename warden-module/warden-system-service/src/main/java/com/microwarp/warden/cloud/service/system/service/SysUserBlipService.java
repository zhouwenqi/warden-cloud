package com.microwarp.warden.cloud.service.system.service;

import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUserBlip;

/**
 * service - 系统用户标记
 * @author zhouwenqi
 */
public interface SysUserBlipService extends BaseService<SysUserBlip> {
    /**
     * 检查用户是否被标记波动
     * @param userId 用户id
     * @param ip ip地址
     * @return
     */
    boolean isBlip(Long userId, String ip);

    /**
     * 添加一条用户波动标记
     * @param userId 用户id
     * @param ip ip地址
     */
    void add(Long userId, String ip);

    /**
     * 删除一条用户波动标记信息
     * @param userId 用户id
     * @param ip ip地址
     */
    void delete(Long userId, String ip);

    /**
     * 清除用户波动标记信息
     * @param userId 用户id
     */
    void clear(Long userId);
}
