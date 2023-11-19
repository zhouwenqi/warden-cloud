package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserRequestDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUser;
import com.microwarp.warden.cloud.service.system.domain.excel.SysUserExcel;
import com.microwarp.warden.cloud.service.system.domain.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 系统用户
 * @author zhouwenqi
 */
@Mapper
public interface SysUserMapstruct {
    SysUserMapstruct Instance = Mappers.getMapper(SysUserMapstruct.class);

    /** entity - dto */
    SysUserDTO sysUserToSysUserDTO(SysUser sysUser);
    SysUser sysUserDtoToSysUser(SysUserDTO sysUserDTO);
    List<SysUserDTO> sysUsersToSysUserDTOs(List<SysUser> sysUsers);
    SysUserDetailsDTO sysUserDtoToSysUserDetailsDTO(SysUserDTO sysUserDTO);

    /** vo - dto */
    SysUserVO sysUserDtoToSysUserVo(SysUserDTO sysUserDTO);
    List<SysUserVO> sysUsersDtoToSysUsersVo(List<SysUserDTO> sysUsersDTO);
    SysUserDTO sysUserRegisterRequestToSysUserDTO(SysUserRegisterRequest sysUserRegisterRequest);
    SysUserDTO sysUserUpdateRequestToSysUserDTO(SysUserUpdateRequest sysUserUpdateRequest);
    SysUserDTO sysUserProfileRequestToSysUserDTO(SysUserProfileRequest sysUserProfileRequest);
    SysUserRequestDTO sysUserCreateRequestToSysUserRequestDTO(SysUserCreateRequest sysUserCreateRequest);
    SysUserDetailsVO sysUserDetailsDtoToSysUserDetailsVo(SysUserDetailsDTO sysUserDetailsDTO);
    SysUserExcel sysUserDtoToSysUserExcel(SysUserDTO sysUserDTO);
    List<SysUserExcel> sysUsersDtoToSysUsersExcel(List<SysUserDTO> sysUsersDTO);

}
