package com.microwarp.warden.cloud.service.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.microwarp.warden.cloud.common.database.domain.BaseEntity;

/**
 * entity - 系统角色
 * @author zhouwenqi
 */
@TableName("wd_sys_role")
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 7524784504482424493L;
    /** 角色名称 */
    private String name;
    /** 角色值 */
    private String value;
    /** 描述 */
    private String description;
    /** 排序值 */
    private Long orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }
}
