package com.microwarp.warden.cloud.service.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.microwarp.warden.cloud.common.core.enums.MessageTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.PlatformTypeEnum;
import com.microwarp.warden.cloud.common.database.domain.LogicEntity;

/**
 * entity - 系统消息
 * @author zhouwenqi
 */
@TableName("wd_sys_message")
public class SysMessage extends LogicEntity {
    private static final long serialVersionUID = -631532429604380860L;
    /** 发送人ID */
    private Long fromId;
    /** 接收人ID */
    private Long toId;
    /** 发送平台 */
    private PlatformTypeEnum fromPlatform;
    /** 接收平台 */
    private PlatformTypeEnum toPlatform;
    /** 消息类型 */
    private MessageTypeEnum msgType;
    /** 消息标题 */
    private String title;
    /** 消息内容 */
    private String content;
    /** 消息源关联ID */
    private Long metaId;
    /** 已读 */
    private Boolean reading;

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public PlatformTypeEnum getFromPlatform() {
        return fromPlatform;
    }

    public void setFromPlatform(PlatformTypeEnum fromPlatform) {
        this.fromPlatform = fromPlatform;
    }

    public PlatformTypeEnum getToPlatform() {
        return toPlatform;
    }

    public void setToPlatform(PlatformTypeEnum toPlatform) {
        this.toPlatform = toPlatform;
    }

    public MessageTypeEnum getMsgType() {
        return msgType;
    }

    public void setMsgType(MessageTypeEnum msgType) {
        this.msgType = msgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getReading() {
        return reading;
    }

    public void setReading(Boolean reading) {
        this.reading = reading;
    }

    public Long getMetaId() {
        return metaId;
    }

    public void setMetaId(Long metaId) {
        this.metaId = metaId;
    }
}
