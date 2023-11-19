package com.microwarp.warden.cloud.common.redis;

import com.microwarp.warden.cloud.common.core.cache.ICacheableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * service - 持久化缓存
 * @author zhouwenqi
 */
@Service
public class DataCacheableService implements ICacheableService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private String getCacheKey(String cacheName,String key){
        return cacheName+"::"+key;
    }
    @Override
    public void batchRemove(String cacheName, String... keys) {
        batchRemove(new String[]{cacheName},keys);
    }

    @Override
    public <T> void save(String cacheName, String key, T t) {
        redisTemplate.opsForValue().set(getCacheKey(cacheName,key),t);
    }

    @Override
    public <T> T get(String cacheName, String key) {
        return (T)redisTemplate.opsForValue().get(getCacheKey(cacheName,key));
    }

    /**
     * 批量移除指定key的缓存
     * @param cacheNames 缓存名列表
     * @param keys 键名列表
     */
    public <T> void batchRemove(String[] cacheNames, String[] keys) {
        if(null == cacheNames || cacheNames.length < 1){
            return;
        }
        for(String name:cacheNames){
            // 没传key将全部清空
            if(null == keys || keys.length <1){
                redisTemplate.delete(name+"*");
                continue;
            }
            try {
                List<String> keyList = new ArrayList<>();
                for (String k : keys) {
                    if(StringUtils.isBlank(k)){
                        continue;
                    }
                    keyList.add(getCacheKey(name,k));

                }
                redisTemplate.delete(keyList);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
