package com.microwarp.warden.cloud.service.system.service;

import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysConfigDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysConfig;

/**
 * service - 配置
 * @author zhouwenqi
 */
public interface SysConfigService extends BaseService<SysConfig> {
    /**
     * 获取当前系统配置
     * @return 系统配置
     */
    SysConfigDTO findCurrent();

    /**
     * 更新系统配置
     * @param sysConfigDTO 系统配置
     */
    void update(SysConfigDTO sysConfigDTO);
}
