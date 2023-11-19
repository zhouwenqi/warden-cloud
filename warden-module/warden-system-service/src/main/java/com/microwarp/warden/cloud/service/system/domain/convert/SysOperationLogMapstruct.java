package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysOperationLogDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysOperationLog;
import com.microwarp.warden.cloud.service.system.domain.vo.SysOperationLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 操作日志
 * @author zhouwenqi
 */
@Mapper
public interface SysOperationLogMapstruct {
    SysOperationLogMapstruct Instance = Mappers.getMapper(SysOperationLogMapstruct.class);

    /** entity - dto */
    SysOperationLog sysOperationLogDtoToSysOperationLog(SysOperationLogDTO sysOperationLogDTO);
    SysOperationLogDTO sysOperationLogToSysOperationLogDTO(SysOperationLog sysOperationLog);
    List<SysOperationLogDTO> sysOperationLogsToSysOperationLogDTOs(List<SysOperationLog> sysOperationLogs);

    /** vo - dto */
    SysOperationLogVO sysOperationLogDtoToSysOperationLogVO(SysOperationLogDTO sysOperationLogDTO);
    List<SysOperationLogVO> sysOperationLogDtosToSysOperationLogVOs(List<SysOperationLogDTO> sysOperationLogDTOs);

}
