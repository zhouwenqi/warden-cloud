package com.microwarp.warden.cloud.service.system.domain.vo;

import java.util.Date;

/**
 * vo - 权限 - response
 */
public class SysPermissionVO {
    private Long id;
    /** 权限名称 */
    private String name;
    /** 权限值 */
    private String value;
    /** 父级权限ID */
    private Long parentId;
    /** 父级权限 */
    private SysPermissionVO parentPermission;
    /** 排序值 */
    private Long orders;
    /** 创建时间 */
    private Date createDate;
    /** 修改时间 */
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public SysPermissionVO getParentPermission() {
        return parentPermission;
    }

    public void setParentPermission(SysPermissionVO parentPermission) {
        this.parentPermission = parentPermission;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
