package com.power.authority.authorization.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @program: authorization
 * @description: 自定义cacheManage的缓存管理器
 *
 *      * ehcache缓存管理器；shiro整合ehcache：
 *      * 这里的cache用使用 redis来实现
 *      * 这里的缓存依然使用cache，在处理http等访问数据、程序计数器方面是优于redis的
 *      * cache 的核心其实是一个线程安全的ConcurrentMap,基于内存、但是会造成大量内存浪费
 *      * cache是单机环境的 应采用spring cache的redis实现，redis可以实现缓存同步
 *      * springboot2.0以后的版本，RedisCacheManager已经没有单参数的构造方法
 *      * @return
 * @author: xie ting
 * @create: 2020-05-11 18:19
 */
public class RedisManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<K,V>();
    }
}
