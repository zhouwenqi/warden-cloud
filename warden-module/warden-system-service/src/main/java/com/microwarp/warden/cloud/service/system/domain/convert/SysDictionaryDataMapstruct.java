package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.common.core.domain.pojo.DictionaryItem;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDataDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionaryData;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDictionaryDataCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDictionaryDataUpdateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDictionaryDataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 对象复制 - SysDictionaryData
 * (dto - entity)
 */
@Mapper
public interface SysDictionaryDataMapstruct {
    SysDictionaryDataMapstruct Instance = Mappers.getMapper(SysDictionaryDataMapstruct.class);
    /** entity - dto */
    SysDictionaryData sysDictionaryDataDtoToSysDictionaryData(SysDictionaryDataDTO sysDictionaryDataDTO);
    SysDictionaryDataDTO sysDictionaryDataToSysDictionaryDataDTO(SysDictionaryData sysDictionaryData);
    List<SysDictionaryDataDTO> sysDictionaryDatasToSysDictionaryDatasDTO(List<SysDictionaryData> sysDictionaryDatas);

    /** vo - dto */
    SysDictionaryDataVO sysDictionaryDataDtoToSysDictionaryDataVO(SysDictionaryDataDTO sysDictionaryDataDTO);
    List<SysDictionaryDataVO> sysDictionaryDataDtosToSysDictionaryDataVOs(List<SysDictionaryDataDTO> sysDictionaryDTOs);
    SysDictionaryDataDTO sysDictionaryDataUpdateRequestToSysDictionaryDataDTO(SysDictionaryDataUpdateRequest sysDictionaryDataRequest);
    SysDictionaryDataDTO sysDictionaryDataCreateRequestToSysDictionaryDataDTO(SysDictionaryDataCreateRequest sysDictionaryDataRequest);
    DictionaryItem sysDictionaryDataDtoToDictionaryItem(SysDictionaryDataDTO sysDictionaryDataDTO);
    List<DictionaryItem> sysDictionaryDatasDtoToDictionaryItems(List<SysDictionaryDataDTO> sysDictionarysDTO);
}
