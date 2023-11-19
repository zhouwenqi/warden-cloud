package com.microwarp.warden.cloud.service.system.dao.impl;

import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPostDTO;
import com.microwarp.warden.cloud.service.system.dao.SysPostDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysPostMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysPost;
import com.microwarp.warden.cloud.service.system.mapper.SysPostMapper;
import org.springframework.stereotype.Repository;

/**
 * dao - 岗位 - impl
 * @author zhouwenqi
 */
@Repository
public class SysPostDaoImpl extends BaseDaoImpl<SysPostMapper,SysPost> implements SysPostDao {
    @Override
    public SysPostDTO findById(Long id){
        SysPost sysPost = getById(id);
        return null != sysPost ? SysPostMapstruct.Instance.sysPostToSysPostDTO(sysPost) : null;
    }
}
