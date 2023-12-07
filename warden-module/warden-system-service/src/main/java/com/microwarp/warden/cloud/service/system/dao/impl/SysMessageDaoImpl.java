package com.microwarp.warden.cloud.service.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.microwarp.warden.cloud.common.core.security.WardenUser;
import com.microwarp.warden.cloud.common.core.util.ResultUtil;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageDTO;
import com.microwarp.warden.cloud.service.system.dao.SysMessageDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysMessageMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysMessage;
import com.microwarp.warden.cloud.service.system.mapper.SysMessageMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

/**
 * dao - 系统消息 - impl
 */
@Repository
public class SysMessageDaoImpl extends BaseDaoImpl<SysMessageMapper,SysMessage> implements SysMessageDao {

    /**
     * 查询一条系统消息
     * @param id 消息ID
     * @param wardenUser 接收用户
     * @return
     */
    public SysMessageDTO findById(Long id, WardenUser wardenUser){
        QueryWrapper<SysMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("to_id", Long.parseLong(wardenUser.getUserId()));
        queryWrapper.eq("to_platform", ResultUtil.convertToPlatform(wardenUser.getUserType()));
        queryWrapper.eq("id",id);
        queryWrapper.last("limit 1");
        SysMessage sysMessage = baseMapper.selectOne(queryWrapper);
        return null == sysMessage ? null : SysMessageMapstruct.Instance.sysMessageToSysMessageDTO(sysMessage);
    }
    /**
     * 设置消息阅读状态
     * @param id 消息ID
     * @param wardenUser 接收人
     * @param status 阅读状态
     */
    public void setReadStatus(Long[] id, WardenUser wardenUser, boolean status){
        if(null != wardenUser) {
            UpdateWrapper<SysMessage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("reading", status);
            updateWrapper.eq("to_id", Long.parseLong(wardenUser.getUserId()));
            updateWrapper.eq("to_platform",  ResultUtil.convertToPlatform(wardenUser.getUserType()));
            // id 为空将设置所有当前接收人消息
            if(null != id && id.length > 0) {
                updateWrapper.in("id", Arrays.asList(id));
            }
            baseMapper.update(null, updateWrapper);
        }
    }

    /**
     * 删除消息
     * @param id 消息ID
     * @param wardenUser 接收人
     */
    public void delete(Long[] id,  WardenUser wardenUser) {
        if(null != id && id.length > 0 && null != wardenUser) {
            QueryWrapper<SysMessage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("to_id", Long.parseLong(wardenUser.getUserId()));
            queryWrapper.eq("to_platform", ResultUtil.convertToPlatform(wardenUser.getUserType()));
            queryWrapper.in("id", Arrays.asList(id));
            baseMapper.delete(queryWrapper);
        }
    }
}
