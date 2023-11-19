package com.microwarp.warden.cloud.service.system.dao.impl;

import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.service.system.dao.SysNoticeDao;
import com.microwarp.warden.cloud.service.system.domain.entity.SysNotice;
import com.microwarp.warden.cloud.service.system.mapper.SysNoticeMapper;
import org.springframework.stereotype.Repository;

/**
 * dao - 系统消息 - impl
 */
@Repository
public class SysNoticeDaoImpl extends BaseDaoImpl<SysNoticeMapper,SysNotice> implements SysNoticeDao {
}
