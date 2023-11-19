package com.microwarp.warden.cloud.facade.system.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * dto - 系统权限
 */
public class SysPermissionDTO implements Serializable {
    private static final long serialVersionUID = 1395047510160307348L;
    private Long id;
    /** 权限名称 */
    private String name;
    /** 权限值 */
    private String value;
    /** 父级权限ID */
    private Long parentId;
    /** 父级权限 */
    private SysPermissionDTO parentPermission;
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

    public SysPermissionDTO getParentPermission() {
        return parentPermission;
    }

    public void setParentPermission(SysPermissionDTO parentPermission) {
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

    @Override
    public boolean equals(Object object){
        if(null == object){
            return false;
        }
        if(this == object){
            return true;
        }
        if(object instanceof SysPermissionDTO){
            SysPermissionDTO sysPermissionDTO = (SysPermissionDTO)object;
            return sysPermissionDTO.value.equals(this.getValue());
        }
        return false;
    }
    @Override
    public int hashCode(){
        return value.hashCode();
    }
}
