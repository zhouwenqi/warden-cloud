package com.microwarp.warden.cloud.service.system.service;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysLoginLogDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysLoginLogSearchDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysLoginLog;

/**
 * service - 登录日志
 * @author zhouwenqi
 */
public interface SysLoginLogService extends BaseService<SysLoginLog> {
    /**
     * 查询登录日志信息
     * @param id 日志id
     * @return
     */
    SysLoginLogDTO findById(Long id);
    /**
     * 获取系统用户最后一条登录信息
     * @param userId 用户id
     * @return
     */
    SysLoginLogDTO findLastByUserId(Long userId);
    /**
     * 添加登录日志信息
     * @param sysLoginLogDTO 日志信息
     */
    void add(SysLoginLogDTO sysLoginLogDTO);
    /**
     * 删除登录日志
     * @param id 日志id
     */
    void delete(Long... id);
    /**
     * 清空登录日志
     */
    void clearAll();
    /**
     * 分页查询登录日志
     * @param iSearchPageable 查询条件
     * @return
     */
    ResultPage<SysLoginLogDTO> findPage(ISearchPageable<SysLoginLogSearchDTO> iSearchPageable);
}
