package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysConfigDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysConfig;
import com.microwarp.warden.cloud.service.system.domain.vo.SysConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象复制 - SysConfig
 * (dto - entity)
 * @author zhouwenqi
 */
@Mapper
public interface SysConfigMapstruct {
    SysConfigMapstruct Instance = Mappers.getMapper(SysConfigMapstruct.class);

    /** entity - dto */
    SysConfigDTO sysConfigToSysConfigDTO(SysConfig sysConfig);
    SysConfig sysConfigDTOtoSysConfig(SysConfigDTO sysConfigDTO);

    /** vo - dto */
    SysConfigDTO sysConfigVOtoSysConfigDTO(SysConfigVO sysConfigVO);
    SysConfigVO sysConfigDTOtoSysConfigVO(SysConfigDTO sysConfigDTO);
}
