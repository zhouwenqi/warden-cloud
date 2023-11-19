package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPostDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysPost;

/**
 * dao - 岗位
 * @author zhouwenqi
 */
public interface SysPostDao extends BaseDao<SysPost> {
    SysPostDTO findById(Long id);
}
