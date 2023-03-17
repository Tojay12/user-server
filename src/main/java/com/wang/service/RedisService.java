package com.wang.service;


import io.lettuce.core.RedisException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * spring redis 工具类
 *
 * @author litianlong
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {

    public final RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setRightPushAllList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存分页数据数据
     *
     * @param key  缓存的键值
     * @param var1 待缓存的数据
     * @return 缓存的对象
     */
    public <T> long setCachePageList(final String key, final List<T> var1) {
        if (hasKey(key)) {
            deleteObject(key);
        }
        AtomicReference<Long> count = null;
        AtomicLong atomicLong = new AtomicLong();
        var1.forEach(t -> {
            if (redisTemplate.opsForList().rightPush(key, t) < 0) {
                atomicLong.addAndGet(1);
            }
        });

        return atomicLong.get();
    }

    /**
     * 获取分页数据
     *
     * @param key      键
     * @param pageSize 页码
     * @param pageNum  条数
     * @return 返回数据
     */
    public <T> List<T> getCachePageList(final String key, long pageSize, long pageNum) {
        return redisTemplate.opsForList().range(key, (pageSize - 1) * pageNum, pageNum * pageSize - 1);
    }

    /**
     * 获取从条数
     *
     * @param key 键
     * @return 返回数据
     */
    public <T> Long getCachePageLisSize(final String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据getCacheMapValue
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获取多个Hash中的数据getCacheMapValue
     *
     * @param key   Redis键
     * @param hKeys map键
     * @return Hash对象集合
     */
    public Boolean hasMapKey(final String key, final Object hKeys) {
        return redisTemplate.opsForHash().hasKey(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * @param key: key
     * @return java.lang.Long
     * @description 自增整数类型缓存
     * @author litianlong
     * @since 2021/3/4 11:16
     */
    public Long opsForValueIncr(String key) {
        Long res = null;
        try {
            res = redisTemplate.opsForValue().increment(key, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RedisException(e);
        }
        return res;
    }


    /**
     * @param key: key
     * @return java.lang.String
     * @description 弹出集合头一个元素
     * @author litianlong
     * @since 2021/3/4 11:15
     */
    public String lpopForString(String key) {
        String s = null;
        try {
            s = (String) redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RedisException(e);
        }
        return s;
    }

    /**
     * @param key:   key
     * @param value: value
     * @return java.lang.Boolean
     * @description 集合尾部放入一个元素
     * @author litianlong
     * @since 2021/3/4 11:16
     */
    public Boolean rpushForString(String key, String value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RedisException(e);
        }
    }

    /**
     * @param key:
     * @return boolean
     * @description 判断key是否存在
     * @author litianlong
     * @since 2021/3/24 18:28
     */
    public boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 计算key值对应的数量
     *
     * @param key
     * @return
     */

    public Integer getSize(String key) {
        Integer num = 0;
        try {
            Long size = redisTemplate.opsForZSet().zCard(key);
            return size.intValue();
        } catch (Exception e) {
            log.warn("getSize {}", key, e);
        }
        return num;
    }

    /**
     * 缓存分页数据  zset
     *
     * @param key  键
     * @param var1 值
     * @return 是否成功
     */
    public <T> boolean setCachePageSet(String key, List<T> var1) {
        if (hasKey(key)) {
            deleteObject(key);
        }
        AtomicBoolean flag = new AtomicBoolean();
        AtomicInteger integer = new AtomicInteger(1);
        var1.forEach(t -> {
            flag.getAndSet(redisTemplate.opsForZSet().add(key, t, integer.getAndAdd(1)));
        });
        return flag.get();
    }

    /**
     * 获取分页数据 zset
     *
     * @param key      键
     * @param <T>      值
     * @param pageSize 页码
     * @param pageNum  条数
     * @return 返回数据
     */
    public <T> Set<T> getPageSet(String key, long pageSize, long pageNum) {
        if (pageSize == 0 && pageNum == -1) {
            return redisTemplate.opsForZSet().range(key, pageSize, pageNum);
        }
        return redisTemplate.opsForZSet().range(key, (pageSize - 1) * pageNum, pageSize * pageNum - 1);
    }

}
