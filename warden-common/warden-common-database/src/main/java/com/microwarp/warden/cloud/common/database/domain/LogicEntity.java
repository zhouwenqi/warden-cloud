package com.microwarp.warden.cloud.common.database.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * entity - 逻辑基类
 * @author zhouwenqi
 */
public class LogicEntity extends BaseEntity {
    private static final long serialVersionUID = 2721157239817788885L;
    /** 逻辑删除 */
    @TableLogic
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
