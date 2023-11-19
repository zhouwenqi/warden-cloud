package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysMessage;
import com.microwarp.warden.cloud.service.system.domain.vo.SysMessageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 对象复制 - SysMessage
 * (dto - entity)
 * @author zhouwenqi
 */
@Mapper
public interface SysMessageMapstruct {
    SysMessageMapstruct Instance = Mappers.getMapper(SysMessageMapstruct.class);

    /** entity - dto */
    SysMessageDTO sysMessageToSysMessageDTO(SysMessage sysMessage);
    SysMessage sysMessageDtoToSysMessage(SysMessageDTO sysMessageDTO);
    List<SysMessageDTO> sysMessagesToSysMessageDTOs(List<SysMessage> sysMessages);

    /** vo - dto */
    SysMessageVO sysMessageDtoToSysMessageVO(SysMessageDTO sysMessageDTO);
    List<SysMessageVO> sysMessageDTOsToSysMessageVOs(List<SysMessageDTO> sysMessageDTOS);
}
