package com.microwarp.warden.cloud.common.core.security;

/**
 * enum - 加解密
 */
public enum  SignType {
    ENCRYPT("加密"),
    DECRYPT("解密");
    private final String tag;
    SignType(String tag){
        this.tag = tag;
    }
}
