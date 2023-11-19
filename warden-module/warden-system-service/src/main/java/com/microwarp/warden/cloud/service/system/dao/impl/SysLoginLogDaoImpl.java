package com.microwarp.warden.cloud.service.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysLoginLogDTO;
import com.microwarp.warden.cloud.service.system.dao.SysLoginLogDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysLoginLogMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysLoginLog;
import com.microwarp.warden.cloud.service.system.mapper.SysLoginLogMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

/**
 * dao - 登录日志 - impl
 */
@Repository
public class SysLoginLogDaoImpl extends BaseDaoImpl<SysLoginLogMapper,SysLoginLog> implements SysLoginLogDao {
    /**
     * 查询操作日志信息
     * @param id 日志id
     * @return
     */
    public SysLoginLogDTO findById(Long id){
        if(null == id){
            return null;
        }
        SysLoginLog sysLoginLog = baseMapper.selectById(id);
        return null == sysLoginLog ? null : SysLoginLogMapstruct.Instance.sysLoginLogToSysLoginLogDTO(sysLoginLog);
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
        QueryWrapper<SysLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("id");
        baseMapper.delete(queryWrapper);
    }

}
