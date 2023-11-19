package com.microwarp.warden.cloud.service.system.service;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageSearchDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysMessage;

/**
 * service - 系统消息
 */
public interface SysMessageService extends BaseService<SysMessage> {
    /**
     * 查询一条系统消息
     * @param id 消息ID
     * @return 消息内容
     */
    SysMessageDTO findById(Long id);

    /**
     * 查询一条系统消息
     * @param id 消息ID
     * @param tokenUser 接收用户
     * @return 消息内容
     */
    SysMessageDTO findById(Long id, TokenUser tokenUser);

    /**
     * 创建一条系统消息
     * @param sysMessageDTO 消息内容
     * @return
     */
    SysMessageDTO create(SysMessageDTO sysMessageDTO);

    /**
     * 批量写入消息(所有用户写一条)
     * @param sysMessageDTO 消息内容
     */
    void writeByAllSysUser(SysMessageDTO sysMessageDTO);

    /**
     * 设计消息已读
     * @param id 消息ID
     * @param tokenUser 接收人
     */
    void read(Long[] id, TokenUser tokenUser);

    /**
     * 设置所有消息已读
     * @param tokenUser 接收人
     */
    void readAll(TokenUser tokenUser);

    /**
     * 删除系统消息
     * @param tokenUser 接收人
     */
    void delete(Long[] id, TokenUser tokenUser);

    /**
     * 获取未读消息数量
     * @param tokenUser 接收人
     * @return
     */
    long totalUnread(TokenUser tokenUser);

    /**
     * 分页系统消息
     * @param iSearchPageable 查询条件
     * @return
     */
    ResultPage<SysMessageDTO> findPage(ISearchPageable<SysMessageSearchDTO> iSearchPageable);
}
