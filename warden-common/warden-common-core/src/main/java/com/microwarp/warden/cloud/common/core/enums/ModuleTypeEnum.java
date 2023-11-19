package com.microwarp.warden.cloud.common.core.enums;

/**
 * enum - 模块类型
 */
public enum ModuleTypeEnum implements BaseEnum {
    SYS_USER(0,"系统用户"),
    SYS_PERMISSION(1,"系统权限"),
    SYS_ROLE(2,"系统角色"),
    SYS_DICTIONARY(3,"系统字典"),
    DICTIONARY_DATA(4,"字典数据"),
    SYS_DEPT(5,"部门"),
    SYS_POST(6,"岗位"),
    LOGIN_LOG(7,"登录日志"),
    OPERATION_LOG(8,"操作日志"),
    SYS_MESSAGE(9,"系统消息"),
    SYS_NOTICE(10,"系统公告");
    int code;
    String tag;
    ModuleTypeEnum(int code, String tag){
        this.code = code;
        this.tag = tag;
    }
}
