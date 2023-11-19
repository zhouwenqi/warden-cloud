package com.microwarp.warden.cloud.common.core.security;

import com.microwarp.warden.cloud.common.core.enums.BaseEnum;

/**
 * enum - 用户类型
 */
public enum UserType implements BaseEnum {
    GUEST(0,"来宾"),
    NORMAL(1,"普通用户"),
    SYSTEM(2,"系统用户");
    int code;
    String tag;
    UserType(int code, String tag){
        this.code = code;
        this.tag = tag;
    }
}
