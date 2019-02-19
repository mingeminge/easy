package com.yzm.majoryc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void set(String key, String value, Long expire, TimeUnit timeType) {
        redisTemplate.opsForValue().set(key, value, expire, timeType);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public void flushToken(String token, Long expire, TimeUnit timeType) {
        redisTemplate.expire(token, expire, timeType);
    }
}
