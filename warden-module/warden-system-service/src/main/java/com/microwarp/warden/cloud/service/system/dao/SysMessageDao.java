package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.core.security.WardenUser;
import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysMessage;

/**
 * dao - 系统消息
 */
public interface SysMessageDao extends BaseDao<SysMessage> {
    /**
     * 查询一条系统消息
     * @param id 消息ID
     * @param wardenUser 接收用户
     * @return
     */
    SysMessageDTO findById(Long id, WardenUser wardenUser);
    /**
     * 设置消息阅读状态
     * @param id 消息ID
     * @param wardenUser 接收人
     * @param status 阅读状态
     */
    void setReadStatus(Long[] id,  WardenUser wardenUser, boolean status);

    /**
     * 删除消息
     * @param id 消息ID
     * @param wardenUser 接收人
     */
    void delete(Long[] id, WardenUser wardenUser);
}
