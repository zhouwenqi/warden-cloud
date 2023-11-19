package com.microwarp.warden.cloud.facade.user.domain.dto;

import java.util.List;

/**
 * dto - 更新用户权限
 */
public class UpdatePermissionsDTO {
    /** 用户id */
    private Long userId;
    /** 权限列表 */
    private List<String> permissionValues;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getPermissionValues() {
        return permissionValues;
    }

    public void setPermissionValues(List<String> permissionValues) {
        this.permissionValues = permissionValues;
    }
}
