package com.microwarp.warden.cloud.common.core.cache;

/**
 * service - 持久化缓存
 * @author zhouwenqi
 */
public interface ICacheableService {
    /**
     * 批量移除指定key的缓存
     * @param cacheName 缓存名
     * @param keys 键名
     */
    void batchRemove(String cacheName, String... keys);

    /**
     * 保存缓存内容
     * @param cacheName 缓存名
     * @param key 键名
     * @param t 缓存内容
     */
    <T> void save(String cacheName, String key, T t);

    /**
     * 获取指定的缓存内容
     * @param cacheName 缓存名
     * @param key 键名
     * @return 缓存内容
     */
    <T> T get(String cacheName, String key);
}
