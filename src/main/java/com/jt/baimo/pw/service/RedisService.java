package com.jt.baimo.pw.service;


import java.util.Date;
import java.util.List;
import java.util.Set;


public interface RedisService {


    void setExpireTime(String key, long time);

    Long getExpire(String key);

    Set<String> keys(String pattern);

    Boolean expireAt(String key, Date date);


    /**
     * 过期只有当天的
     * @param key
     * @return
     */
    Boolean expireAtTomorrow(String key);

    boolean set(String key, String value);

    boolean set(String key, String value, long time);

    String get(String key);

    Integer getInt(String key);

    List<String> mGet(List<String> keys);

    void del(String... key);

    boolean hasKey(String key);

    long incr(String key);

    long incr(String key, long startVal);

    long incr(String key, long startVal, long increment);




    Long sAdd(String key, String... values);

    Set<String> setMembers(String key);

    Long sSize(String key);

    Boolean sIsMember(String key, Object value);

    List<String> lRange(String key, long start, long end);

    Long lRightPush(String key, String value);

    void lTrim(String key, long start, long end);

    Long lLen(String key);

    Long lRemove(String key, long index, String value);

//    void saveGeo(String key, Double lng, Double lat, String cityCode);

//    double distance(String key, String cityCode1, String cityCode2, DistanceUnit distanceUnit);
}
