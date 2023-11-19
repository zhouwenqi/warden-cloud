package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysOperationLogDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysOperationLog;

/**
 * dao - 操作日志
 */
public interface SysOperationLogDao extends BaseDao<SysOperationLog> {
    /**
     * 查询操作日志信息
     * @param id 日志id
     * @return
     */
    SysOperationLogDTO findById(Long id);
    /**
     * 删除操作日志
     * @param id 日志id
     */
    void delete(Long... id);

    /**
     * 清空操作日志
     */
    void clearAll();
}
