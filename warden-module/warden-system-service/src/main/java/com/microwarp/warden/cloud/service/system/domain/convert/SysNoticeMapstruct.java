package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysNoticeDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysOperationLogDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysNotice;
import com.microwarp.warden.cloud.service.system.domain.entity.SysOperationLog;
import com.microwarp.warden.cloud.service.system.domain.vo.SysNoticeCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysNoticeUpdateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysNoticeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 系统公告
 * @author zhouwenqi
 */
@Mapper
public interface SysNoticeMapstruct {
    SysNoticeMapstruct Instance = Mappers.getMapper(SysNoticeMapstruct.class);

    /** entity - dto */
    SysNoticeDTO sysNoticeToSysNoticeDTO(SysNotice sysNotice);
    SysNotice sysNoticeDtoToSysNotice(SysNoticeDTO sysNoticeDTO);
    List<SysNoticeDTO> sysNoticesToSysNoticeDTOs(List<SysNotice> sysNotices);

    /** vo - dto */
    SysNoticeVO sysNoticeDtoToSysNoticeVO(SysNoticeDTO sysNoticeDTO);
    List<SysNoticeVO> sysNoticeDTOsToSysNoticeVOs(List<SysNoticeDTO> sysNoticeDTOS);
    SysNoticeDTO sysNoticeCreateRequestToSysNoticeDTO(SysNoticeCreateRequest sysNoticeCreateRequest);
    SysNoticeDTO sysNoticeUpdateRequestToSysNoticeDTO(SysNoticeUpdateRequest sysNoticeUpdateRequest);
}
