package com.microwarp.warden.cloud.facade.system.domain.dto;

import java.util.Set;

/**
 * dto - 系统角色详情
 */
public class SysRoleDetailsDTO extends SysRoleDTO {
    private static final long serialVersionUID = 7107617538831563520L;
    /** 权限列表 */
    private Set<SysPermissionDTO> permissions;

    public Set<SysPermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SysPermissionDTO> permissions) {
        this.permissions = permissions;
    }
}
