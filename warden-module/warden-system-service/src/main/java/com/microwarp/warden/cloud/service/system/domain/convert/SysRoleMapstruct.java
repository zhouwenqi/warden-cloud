package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.common.security.authenticator.SecurityAuthority;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysRoleDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysRoleDetailsDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysRole;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUser;
import com.microwarp.warden.cloud.service.system.domain.vo.SysRoleCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysRoleDetailsVO;
import com.microwarp.warden.cloud.service.system.domain.vo.SysRoleUpdateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

/**
 * convert - 系统角色
 * @author zhouwenqi
 */
@Mapper
public interface SysRoleMapstruct {
    SysRoleMapstruct Instance = Mappers.getMapper(SysRoleMapstruct.class);

    /** entity - dto */
    SysRoleDTO sysRoleToSysRoleDTO(SysRole sysRole);
    SysRoleDetailsDTO sysRoleDtoToSysRoleDetailsDTO(SysRoleDTO sysRoleDTO);
    SysRole sysRoleDtoToSysSysRole(SysRoleDTO sysRoleDTO);
    List<SysRoleDTO> sysRolesToSysRoleDTOs(List<SysRole> sysRoles);

    /** vo - dto */
    SysRoleVO sysRoleDtoToSysRoleVO(SysRoleDTO sysRoleDTO);
    SysRoleDTO sysRoleUpdateRequestTosysRoleDTO(SysRoleUpdateRequest sysRoleUpdateRequest);
    SysRoleDTO sysRoleCreateRequestTosysRoleDTO(SysRoleCreateRequest sysRoleCreateRequest);
    List<SysRoleDTO> sysRoleDtosToSysRoleVOs(List<SysRoleDTO> sysRoleDTOs);
    SysRoleDetailsVO sysRoleDetailsDtoToSysRoleDetailsVO(SysRoleDetailsDTO sysRoleDetailsDTO);
    @Mapping(target = "authority", source = "value")
    SecurityAuthority toSecurityAuthority(SysRoleDTO sysRoleDTO);
    List<SecurityAuthority> toSecurityAuthoritys(Set<SysRoleDTO> sysRoleDTOs);

}
