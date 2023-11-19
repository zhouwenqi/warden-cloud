package com.microwarp.warden.cloud.facade.user.domain.dto;

import com.microwarp.warden.cloud.common.core.security.Permission;

import java.io.Serializable;
import java.util.List;

/**
 * dto - user
 */
public class UserDetailsDTO extends UserDTO implements Serializable {
    private static final long serialVersionUID = -2905936773929861291L;
    private List<String> permissionValues;

    public List<String> getPermissionValues() {
        return permissionValues;
    }

    public void setPermissionValues(List<String> permissionValues) {
        this.permissionValues = permissionValues;
    }
}
