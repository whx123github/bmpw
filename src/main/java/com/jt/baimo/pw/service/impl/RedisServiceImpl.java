package com.jt.baimo.pw.service.impl;


import com.jt.baimo.pw.service.RedisService;
import org.joda.time.LocalDate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;



@Service
public class RedisServiceImpl implements RedisService {


    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> redisTemplate;


    @Resource(name = "redisTemplate2")
    private RedisTemplate<String, String> redisTemplate2;

    /***
     * 根据key重新设置数据的过期事件
     */
    @Override
    public void setExpireTime(String key, long time) {
        try {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key
     * @param unit
     * @return
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 过期时间
     *
     * @param key
     * @return
     */
    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /***
     * 设置过期时间
     * @param key
     * @param date
     * @return
     */
    @Override
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    @Override
    public Boolean expireAtTomorrow(String key) {
        return redisTemplate.expireAt(key, new LocalDate().plusDays(1).toDate());
    }


    /**
     * 放入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 放入缓存并设置过期时间
     *
     * @param key
     * @param value
     * @param time  时间为负数,永久保存
     * @return
     */
    @Override
    public boolean set(String key, String value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据key值获取缓存的值
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    @Override
    public Integer getInt(String key) {
        try {
            Integer result = key == null ? 0 : Integer.valueOf(redisTemplate2.opsForValue().get(key));
            return result;
        } catch (Exception e) {
            return 0;
        }
    }


    @Override
    public List<String> mGet(List<String> keys) {
        if (keys == null || keys.size() == 0) {
            return null;
        } else {
            List<String> values = redisTemplate.opsForValue().multiGet(keys);
            values.remove(null);
            return values;
        }
    }


    /***
     * 删除缓存
     * @param key
     */
    @Override
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    /***
     * 判断缓存是否包含该值
     * @param key
     * @return
     */
    @Override
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }


    @Override
    public long incr(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    @Override
    public long incr(String key, long startVal, long increment) {
        if (!hasKey(key)) {
            redisTemplate2.opsForValue().set(key, String.valueOf(startVal));
            return startVal;
        }
        return redisTemplate2.opsForValue().increment(key, increment);
    }






    /*********************************************set相关操作************************************************************/
    @Override
    public Long sAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 获取集合所有元素
     *
     * @param key
     * @return
     */
    @Override
    public Set<String> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取集合的大小
     *
     * @param key
     * @return
     */
    @Override
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    @Override
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /*******************************************************List****************************************************/

    /**
     * 获取列表指定范围内的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<String> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }


    @Override
    public Long lRightPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }


    @Override
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key
     * @param index
     *            index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *            index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value
     * @return
     */
    @Override
    public Long lRemove(String key, long index, String value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

}
