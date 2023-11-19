package com.microwarp.warden.cloud.service.system.dao.impl;

import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.service.system.dao.SysConfigDao;
import com.microwarp.warden.cloud.service.system.domain.entity.SysConfig;
import com.microwarp.warden.cloud.service.system.mapper.SysConfigMapper;
import org.springframework.stereotype.Repository;

/**
 * dao - 配置 - impl
 * @author zhouwenqi
 */
@Repository
public class SysConfigDaoImpl extends BaseDaoImpl<SysConfigMapper,SysConfig> implements SysConfigDao {
}
