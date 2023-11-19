package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysLoginLogDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysLoginLog;

/**
 * dao - 登录日志
 */
public interface SysLoginLogDao extends BaseDao<SysLoginLog> {
    /**
     * 查询登录日志信息
     * @param id 日志id
     * @return
     */
    SysLoginLogDTO findById(Long id);
    /**
     * 删除登录日志
     * @param id 日志id
     */
    void delete(Long... id);

    /**
     * 清空登录日志
     */
    void clearAll();
}
