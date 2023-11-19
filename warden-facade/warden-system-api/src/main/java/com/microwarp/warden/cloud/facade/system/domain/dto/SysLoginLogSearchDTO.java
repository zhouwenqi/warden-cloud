package com.microwarp.warden.cloud.facade.system.domain.dto;

import com.microwarp.warden.cloud.common.core.enums.ActionStatusEnum;
import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;

/**
 * dto -  登录日志过滤查询
 */
public class SysLoginLogSearchDTO extends BasicSearchDTO {
    /** 用户id */
    private Long userId;
    /** 状态 */
    private ActionStatusEnum[] status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ActionStatusEnum[] getStatus() {
        return status;
    }

    public void setStatus(ActionStatusEnum[] status) {
        this.status = status;
    }
}
