package com.microwarp.warden.cloud.service.system.domain.vo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * vo - 更新用户权限 - update
 */
public class UpdateUserPermissionRequest {
    /** 用户id */
    @NotNull(message = "用户id不能为空")
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
