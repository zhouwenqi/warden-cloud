package com.microwarp.warden.cloud.service.system.domain.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * vo - 创建权限信息 - request
 */
public class SysPermissionCreateRequest {
    /** 权限名称 */
    @NotBlank(message = "权限名不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]{4,40}",message = "权限名只能是4-40个中文或数字或英文字符")
    private String name;
    /** 权限值 */
    @NotBlank(message = "权限值不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_:]{6,50}",message = "权限值只能是a-zA-Z0-9_:等6-50位字符")
    private String value;
    /** 父级权限ID */
    @Min(value = 0,message = "上级权限ID错误")
    @Max(value = Long.MAX_VALUE,message = "上级权限ID错误")
    private Long parentId;
    /** 排序值 */
    @Max(value = Long.MAX_VALUE, message = "排序值过大")
    @Min(value = 0, message = "排序值最小是0")
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }
}
