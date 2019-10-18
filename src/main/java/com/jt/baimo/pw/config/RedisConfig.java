package com.jt.baimo.pw.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis缓存
 * Created by yb_li on 2019/1/7.
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


    /***
     * redis的自增保存的是数值
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate2(RedisConnectionFactory redisConnectionFactory) {
        // 1.创建 redisTemplate 模版
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 2.关联 redisConnectionFactory
        template.setConnectionFactory(redisConnectionFactory);
        // 3.创建 序列化类
        GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(Object.class);
        // 6.序列化类，对象映射设置
        // 7.设置 value 的转化格式和 key 的转化格式
        template.setValueSerializer(genericToStringSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }


    /**
     * <p>
     * key-vlaue为String，String的reidsTemplate
     * </P>
     * RedisTemplate配置
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        // 配置redisTemplate
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);


        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key 序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key 序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列haul
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }






    /**
     * 缓存的条件生成器 KeyGenerator
     * @return
     */
    @Bean
    public KeyGenerator conditionGenerator() {
        return (target, method, params) -> {
            JSONArray jsonArray = JSONArray.parseArray(JSONObject.toJSONString(params));
            StringBuffer stringBuffer = new StringBuffer();
            jsonArray.forEach(o -> {
                JSONObject jsonObject = (JSONObject) o;
                jsonObject.forEach((key, value) -> {
                    String appendData = key + ":" + value+",";
                    stringBuffer.append(appendData);
                });
            });
            return stringBuffer.toString();
        };
    }
}
