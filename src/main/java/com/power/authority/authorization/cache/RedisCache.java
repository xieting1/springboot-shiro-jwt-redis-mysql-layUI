package com.power.authority.authorization.cache;

import com.power.authority.authorization.util.JedisUtils;
import com.power.authority.authorization.util.JwtUtils;
import com.power.authority.authorization.util.SecurityConsts;
import com.power.authority.authorization.util.SerializeUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: authorization
 * @description: 重写shiro的cache 通过redis/jedis的方法实现
 * @author: xie ting
 * @create: 2020-05-11 17:31
 */
@Component
public class RedisCache<K,V> implements Cache<K,V> {

//    /**
//     * redis-key-前缀-shiro:cache:
//     */
//    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

//    /**
//     * 过期时间-5分钟
//     */
//    private static final Integer EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key){
        return SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + JwtUtils.getUsername(key.toString());
    }

    /**
     * 判断换存是否存在当前值
     * @return
     */
    public boolean exists(Object k){
        if(JedisUtils.exists((String) k)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 缓存设置 过期时长
     * @return
     */
    public Object set(Object k, Object v,long expireTime){
        return (Object) JedisUtils.setObject((String)k,v, (int) expireTime);
    }

    @Override
    public Object get(Object k) throws CacheException {
        if(!JedisUtils.exists((String) k)){
            return null;
        }
        return JedisUtils.getObject((String) k);
    }

    @Override
    public Object put(Object k, Object v) throws CacheException {
        return JedisUtils.setObject((String) k,v);
    }

    @Override
    public Object remove(Object k) throws CacheException {
        return JedisUtils.delKey((String) k);
    }

    @Override
    public void clear() throws CacheException {
        JedisUtils.getJedis().flushDB();
    }

    @Override
    public int size() {
        Long size = JedisUtils.getJedis().dbSize();
        return size.intValue();
    }

    @Override
    public Set<K> keys() {
        Set<byte[]> keys = JedisUtils.getJedis().keys(new String("*").getBytes());
        Set<K> set = new HashSet<K>();
        for (byte[] bs : keys) {
            set.add((K) SerializeUtils.unserializable(bs));
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            values.add(JedisUtils.getObject(this.getKey(key)));
        }
        return (Collection<V>) values;
    }
}
