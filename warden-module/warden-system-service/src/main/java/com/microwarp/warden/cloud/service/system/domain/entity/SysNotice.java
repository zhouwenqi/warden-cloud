package com.microwarp.warden.cloud.service.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.microwarp.warden.cloud.common.database.domain.LogicEntity;

/**
 * entity - 系统公告
 */
@TableName("wd_sys_notice")
public class SysNotice extends LogicEntity {
    private static final long serialVersionUID = -5603463715107141077L;
    /** 公告标题 */
    private String title;
    /** 公告内容 */
    private String content;
    /** 已推送 */
    private Boolean served;
    /** 禁用 */
    private Boolean disabled;

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

    public Boolean getServed() {
        return served;
    }

    public void setServed(Boolean served) {
        this.served = served;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
