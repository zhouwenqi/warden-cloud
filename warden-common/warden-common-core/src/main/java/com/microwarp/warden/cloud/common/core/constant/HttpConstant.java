package com.microwarp.warden.cloud.common.core.constant;

/**
 * http - 常量
 * @author zhouwenqi
 */
public class HttpConstant {
    /** feign远程请求路径前缀 */
    public static final String FEIGN_URI_PREFIX = "/feign";
    /** 统一返回验证结果字段 */
    public static final String RESULT_VALID_KEY = "validFields";
    /** 统一Token凭据请求字段 */
    public static final String HEADER_TOKEN_KEY = "Warden-Token";
    /** 统一GuestID请求字段 */
    public static final String HEADER_GUEST_KEY = "Guest-Id";
    /** feign请求头字段 */
    public static final String HEAD_FEIGN_TOKEN_KEY = "Feign-Token";
    /** 全局默认分页大小 */
    public static final int DEFAULT_PAGE_SIZE = 10;
}
