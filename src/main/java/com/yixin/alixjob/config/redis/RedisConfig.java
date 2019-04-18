package com.yixin.alixjob.config.redis;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 设置数据存入redis 的序列化方式
     *</br>redisTemplate序列化默认使用的jdkSerializeable,存储二进制字节码,导致key会出现乱码，所以自定义
     *序列化类
     *
     * @paramredisConnectionFactory
     */
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    @Bean
    public StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringTemplate = new StringRedisTemplate();
        stringTemplate.setConnectionFactory(factory);
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        stringTemplate.setKeySerializer(redisSerializer);
        stringTemplate.setHashKeySerializer(redisSerializer);
        stringTemplate.setValueSerializer(redisSerializer);
        return stringTemplate;
    }
}
