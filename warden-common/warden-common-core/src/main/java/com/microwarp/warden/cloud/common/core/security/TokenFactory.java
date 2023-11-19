package com.microwarp.warden.cloud.common.core.security;
/**
 * interface - token factory
 */
public interface TokenFactory {
    String generate(WardenUser wardenUser) throws Exception;
    WardenUser parse(String token) throws Exception;
    long getGenerateTime(String token) throws Exception;
}
