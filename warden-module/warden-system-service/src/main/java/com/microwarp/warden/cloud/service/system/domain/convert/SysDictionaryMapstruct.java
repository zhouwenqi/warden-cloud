package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionary;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDictionaryCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDictionaryUpdateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDictionaryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 对象复制 - SysDictionary
 * (dto - entity)
 * @author zhouwenqi
 */
@Mapper
public interface SysDictionaryMapstruct {
    SysDictionaryMapstruct Instance = Mappers.getMapper(SysDictionaryMapstruct.class);

    /** entity - dto */
    SysDictionary sysDictionaryDtoToSysDictionary(SysDictionaryDTO sysDictionaryDTO);
    SysDictionaryDTO sysDictionaryToSysDictionaryDTO(SysDictionary sysDictionary);
    List<SysDictionaryDTO> sysDictionarysToSysDictionaryDTOs(List<SysDictionary> sysDictionaries);

    /** vo - dto */
    SysDictionaryVO sysDictionaryDtoToSysDictionaryVO(SysDictionaryDTO sysDictionaryDTO);
    List<SysDictionaryVO> sysDictionaryDtosToSysDictionaryVOs(List<SysDictionaryDTO> sysDictionaryDTOs);
    SysDictionaryDTO sysDictionaryCreateRequestToSysDictionaryDTO(SysDictionaryCreateRequest sysDictionaryCreateRequest);
    SysDictionaryDTO sysDictionaryUpdateRequestToSysDictionaryDTO(SysDictionaryUpdateRequest sysDictionaryUpdateRequest);
}
