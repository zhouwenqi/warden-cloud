package com.microwarp.warden.cloud.service.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysOperationLogDTO;
import com.microwarp.warden.cloud.service.system.dao.SysOperationLogDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysOperationLogMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysOperationLog;
import com.microwarp.warden.cloud.service.system.mapper.SysOperationLogMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

/**
 * dao - 操作日志 - impl
 */
@Repository
public class SysOperationLogDaoImpl extends BaseDaoImpl<SysOperationLogMapper,SysOperationLog> implements SysOperationLogDao {
    /**
     * 查询操作日志信息
     * @param id 日志id
     * @return
     */
    public SysOperationLogDTO findById(Long id){
        if(null == id){
            return null;
        }
        SysOperationLog sysOperationLog = baseMapper.selectById(id);
        return null == sysOperationLog ? null : SysOperationLogMapstruct.Instance.sysOperationLogToSysOperationLogDTO(sysOperationLog);
    }

    /**
     * 删除操作日志
     * @param id 日志id
     */
    public void delete(Long...id){
        if(null == id || id.length < 1){
            return;
        }
        baseMapper.deleteBatchIds(Arrays.asList(id));
    }

    /**
     * 清空操作日志
     */
    public void clearAll(){
        QueryWrapper<SysOperationLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("id");
        baseMapper.delete(queryWrapper);
    }

}
