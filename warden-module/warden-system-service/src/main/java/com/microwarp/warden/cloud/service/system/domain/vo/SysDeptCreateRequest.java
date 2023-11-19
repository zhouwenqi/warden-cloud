package com.microwarp.warden.cloud.service.system.domain.vo;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * vo - 创建部门 - request
 */
public class SysDeptCreateRequest {
    /** 部门名称 */
    @NotBlank(message = "部门名称不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]{4,40}",message = "部门名称只能是4-40个中文或数字或英文字符")
    private String name;
    /** 部门电话 */
    private String phone;
    /** 部门编号 */
    @NotBlank(message = "部门编号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}",message = "部门编号只能是6-20位字母和数字字符")
    private String code;
    /** 上级部门ID */
    @Min(value = 0,message = "上级部门ID错误")
    @Max(value = Long.MAX_VALUE,message = "上级部门ID错误")
    private Long parentId;
    /** 负责人id */
    private Long leaderId;
    /** 描述 */
    private String description;
    /** 排序值 */
    @Max(value = Long.MAX_VALUE, message = "排序值过大")
    @Min(value = 0, message = "排序值最小是0")
    private Long orders;
    /** 禁用 */
    private Boolean disabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
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
