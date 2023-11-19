package com.microwarp.warden.cloud.service.system.constant;

/**
 * security - 常量
 */
public class SecurityConstant {
    /** 超级管理员角色默认值 */
    public static final String ROLE_ROOT_VALUE = "role:super";
    /** 管理员角色默认值 */
    public static final String ROLE_ADMIN_VALUE = "role:admin";
    /** 保留超管用户名 */
    public static final String RESERVE_ROOT_USER_NAME = "superman";
    /** 登录失败次数限制 */
    public static final int LOGIN_COUNT_LIMIT = 5;
    /** IP锁定上限 */
    public static final int IP_LOCK_LIMIT = 3;
    /** 锁定时间/ip(分钟) */
    public static final int LOGIN_LOCK_TIME = 10;
}
