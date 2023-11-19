package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysLoginLogDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysLoginLog;
import com.microwarp.warden.cloud.service.system.domain.excel.SysLoginLogExcel;
import com.microwarp.warden.cloud.service.system.domain.vo.SysLoginLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 登录日志
 * @author zhouwenqi
 */
@Mapper
public interface SysLoginLogMapstruct {
    SysLoginLogMapstruct Instance = Mappers.getMapper(SysLoginLogMapstruct.class);

    /** entity - dto */
    SysLoginLog sysLoginLogDtoToSysLoginLog(SysLoginLogDTO sysLoginLogDTO);
    SysLoginLogDTO sysLoginLogToSysLoginLogDTO(SysLoginLog sysLoginLog);
    List<SysLoginLogDTO> sysLoginLogsToSysLoginLogDTOs(List<SysLoginLog> sysLoginLogs);

    /** vo - dto */
    SysLoginLogVO sysLoginLogDtoToSysLoginLogVO(SysLoginLogDTO sysLoginLogDTO);
    List<SysLoginLogVO> sysLoginLogDtosToSysLoginLogVOs(List<SysLoginLogDTO> sysLoginLogsDTO);
    SysLoginLogExcel sysLoginLogDtoToSysLoginLogExcel(SysLoginLogDTO sysLoginLogDTO);
    List<SysLoginLogExcel> sysLoginLogDtosToSysLoginLogExcels(List<SysLoginLogDTO> sysLoginLogDTOs);
}
