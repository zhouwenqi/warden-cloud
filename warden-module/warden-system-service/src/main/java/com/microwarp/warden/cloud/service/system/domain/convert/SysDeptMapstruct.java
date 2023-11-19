package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptTreeDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysRoleDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDept;
import com.microwarp.warden.cloud.service.system.domain.entity.SysRole;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDeptCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDeptTreeVO;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDeptUpdateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDeptVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 部门
 * @author zhouwenqi
 */
@Mapper
public interface SysDeptMapstruct {
    SysDeptMapstruct Instance = Mappers.getMapper(SysDeptMapstruct.class);
    /** entity - dto */
    SysDeptDTO sysDeptToSysDeptDTO(SysDept sysDept);
    SysDept sysDeptDtoToSysDept(SysDeptDTO sysDeptDTO);
    SysDeptTreeDTO sysDeptToSysDeptTreeDTO(SysDept sysDept);
    List<SysDeptDTO> sysDeptsToSysDeptDTOs(List<SysDept> sysDepts);
    List<SysDeptTreeDTO> sysDeptsToSysDeptTreeDTOs(List<SysDept> sysDepts);

    /** vo - dto */
    SysDeptVO sysDeptDtoToSysDeptVO(SysDeptDTO sysDeptDTO);
    List<SysDeptVO> sysDeptDtosToSysDeptsVOs(List<SysDeptDTO> sysDeptDTOs);
    SysDeptDTO sysDeptCreateRequestToSysDeptDTO(SysDeptCreateRequest sysDeptRequest);
    SysDeptDTO sysDeptUpdateRequestToSysDeptDTO(SysDeptUpdateRequest sysDeptRequest);
    SysDeptTreeVO sysDeptTreeDtoToSysDeptTreeVO(SysDeptTreeDTO sysDeptTreeDTO);
    List<SysDeptTreeVO> sysDeptTreeDtosToSysDeptTreesVOs(List<SysDeptTreeDTO> sysDeptTreeDTOs);

}
