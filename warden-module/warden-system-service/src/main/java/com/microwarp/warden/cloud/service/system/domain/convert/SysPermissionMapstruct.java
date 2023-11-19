package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.common.security.authenticator.SecurityAuthority;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPermissionDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPermissionTreeDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysPermission;
import com.microwarp.warden.cloud.service.system.domain.excel.SysPermissionExcel;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPermissionCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPermissionTreeVO;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPermissionUpdateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

/**
 * convert - 系统权限
 * @author zhouwenqi
 */
@Mapper
public interface SysPermissionMapstruct {
    SysPermissionMapstruct Instance = Mappers.getMapper(SysPermissionMapstruct.class);

    /** entity - dto */
    SysPermissionDTO sysPermissionToSysPermissionDTO(SysPermission sysPermission);
    SysPermission sysPermissionDtoToSysPermission(SysPermissionDTO sysPermissionDTO);
    SysPermissionTreeDTO sysPermissionToSysPermissionTreeDTO(SysPermission sysPermission);
    List<SysPermissionDTO> sysPermissionsToSysPermissionDTOs(List<SysPermission> sysPermissions);
    List<SysPermissionTreeDTO> sysPermissionsToSysPermissionTreeDTOs(List<SysPermission> sysPermissions);

    /** vo - dto */
    @Mapping(target = "authority", source = "value")
    SecurityAuthority toSecurityAuthority(SysPermissionDTO sysPermissionDTO);
    List<SecurityAuthority> toSecurityAuthoritys(Set<SysPermissionDTO> sysPermissionDTOs);
    SysPermissionVO sysPermissionDtoToSysPermissionVO(SysPermissionDTO sysPermissionDTO);
    List<SysPermissionVO> sysPermissionDtosToSysPermissionVOs(List<SysPermissionDTO> sysPermissionDTOs);
    SysPermissionDTO sysPermissionCreateRequestToSysPermissionDTO(SysPermissionCreateRequest sysPermissionRequest);
    SysPermissionDTO sysPermissionUpdateRequestToSysPermissionDTO(SysPermissionUpdateRequest sysPermissionRequest);
    SysPermissionExcel sysPermissionDtoToSysPermissionExcel(SysPermissionDTO sysPermissionDTO);
    List<SysPermissionExcel> sysPermissionDtosToSysPermissionExcels(List<SysPermissionDTO> sysPermissionDTOs);
    SysPermissionTreeVO sysPermissionTreeDtoToSysPermissionTreeVO(SysPermissionTreeDTO sysPermissionTreeDTO);
    List<SysPermissionTreeVO> sysPermissionTreeDtosToSysPermissionTreeVOs(List<SysPermissionTreeDTO> sysPermissionTreeDTOs);
}
