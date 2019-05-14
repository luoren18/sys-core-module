package top.luoren.basis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author luoren
 * @date 2019-05-14 17:30
 */

@Service
public class RedisService<K, V> {

    private @Autowired
    RedisTemplate<K, V> redisTemplate;

    /**
     * 写入缓存
     */
    public boolean set(final K key, V value) {
        boolean result = false;
        try {
            ValueOperations<K, V> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间,时间单位秒（s）
     */
    public boolean set(final K key, V value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<K, V> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间,自定义时间类型
     */
    public boolean set(final K key, V value, TimeUnit timeUnit, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<K, V> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     */
    public void remove(final K... keys) {
        for (K key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     */
    public void removePattern(final K pattern) {
        Set<K> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     */
    public void remove(final K key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     */
    public boolean exists(final K key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     */
    public V get(final K key) {
        V result = null;
        ValueOperations<K, V> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     */
    public void hmSet(K key, K hashKey, V value) {
        HashOperations<K, K, V> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     */
    public V hmGet(K key, V hashKey) {
        HashOperations<K, K, V> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     */
    public void lPush(K k, V v) {
        ListOperations<K, V> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     */
    public List<V> lRange(K k, long l, long l1) {
        ListOperations<K, V> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     */
    public void add(K key, V value) {
        SetOperations<K, V> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     */
    public Set<V> setMembers(K key) {
        SetOperations<K, V> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     */
    public void zAdd(K key, V value, double scoure) {
        ZSetOperations<K, V> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     */
    public Set<V> rangeByScore(K key, double scoure, double scoure1) {
        ZSetOperations<K, V> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

}

