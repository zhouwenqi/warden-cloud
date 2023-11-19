package com.microwarp.warden.cloud.common.redis.constant;

/**
 * cache keys
 * @author zhouwenqi
 */
public class CacheKeys {
    /** key 分割符 */
    public static final String KEY_SPLIT = ":";
    /** token缓存前缀 */
    public static final String TOKEN_PREFIX = "WD_TOKEN";
    /** 在线token列表前缀 */
    public static final String ONLINE_PREFIX = "WD_ONLINE";
    /** 来宾id前缀 */
    public static final String GUEST_ID_PREFIX = "WD_GUEST_ID";
    /** 图形验证码前缀 */
    public static final String CAPTCHA_IMAGE_CODE_PREFIX = "CIC";
    /** 后台登录失败统计前缀 */
    public static final String LOGIN_SYS_FAILED_PREFIX = "LOGIN_SYS_FAILED";
    /** 客户登录失败统计前缀 */
    public static final String LOGIN_CLI_FAILED_PREFIX = "LOGIN_CLI_FAILED";
    /** 图形验证码缓存 */
    public static final String IMAGE_CAPTCHA_CODE = "ICC";
    /** 持久层配置缓存key */
    public static final String DATA_SYS_CONFIG = "DATA_CONFIG";
    /** 持久层配部门树形数据缓存key */
    public static final String DATA_DEPT_TREE = "DATA_DEPT_TREE";
    /** 持久层系统用户id缓存key */
    public static final String DATA_SYS_USER_ID = "DATA_SYS_USER_ID";
    /** 持久层系统用户帐号缓存key */
    public static final String DATA_SYS_USER_UID = "DATA_SYS_USER_UID";
    /** 持久层系统权限缓存key */
    public static final String DATA_SYS_PERMISSION_TREE = "DATA_SYS_PERMISSION_TREE";
    /** 持久层字典数据缓存key */
    public static final String DATA_DICT_DATAS = "DATA_DICT_DATAS";

}
