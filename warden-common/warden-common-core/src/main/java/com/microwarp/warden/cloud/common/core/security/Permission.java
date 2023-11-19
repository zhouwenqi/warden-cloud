package com.microwarp.warden.cloud.common.core.security;

import java.io.Serializable;

/**
 * 权限
 * @author zhouwenqi
 */
public class Permission implements Serializable {
    private static final long serialVersionUID = 4967097494987324375L;
    /** 权限名角色名 */
    private String name;
    /** 权限值 */
    private String value;

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
}
