package com.microwarp.warden.cloud.service.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.microwarp.warden.cloud.common.database.domain.LogicEntity;

/**
 * entity - 字典
 * @author zhouwenqi
 */
@TableName("wd_sys_dictionary")
public class SysDictionary extends LogicEntity {
    private static final long serialVersionUID = -864451596378861604L;
    /** 字典名 */
    private String name;
    /** 字典编码 */
    private String code;
    /** 描述 */
    private String description;
    /** 排序值 */
    private Long orders;
    /** 禁用 */
    private Boolean disabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
