package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserLockDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUser;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUserLock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 用户锁
 * @author zhouwenqi
 */
@Mapper
public interface SysUserLockMapstruct {
    SysUserLockMapstruct Instance = Mappers.getMapper(SysUserLockMapstruct.class);

    /** entity - dto */
    SysUserLockDTO sysUserLockToSysUserLockDTO(SysUserLock sysUserLock);
}
