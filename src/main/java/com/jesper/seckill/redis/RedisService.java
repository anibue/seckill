package com.jesper.seckill.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        String realKey = prefix.getPrefix() + key;
        try {
            Object value = redisTemplate.opsForValue().get(realKey);
            return clazz.cast(value);
        } catch (Exception e) {
            log.error("Redis get error: {}", e.getMessage());
            return null;
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        String realKey = prefix.getPrefix() + key;
        try {
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                redisTemplate.opsForValue().set(realKey, value);
            } else {
                redisTemplate.opsForValue().set(realKey, value, seconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis set error: {}", e.getMessage());
            return false;
        }
    }

    public <T> boolean setIfAbsent(KeyPrefix prefix, String key, T value) {
        String realKey = prefix.getPrefix() + key;
        try {
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(realKey, value));
            } else {
                return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(realKey, value, seconds, TimeUnit.SECONDS));
            }
        } catch (Exception e) {
            log.error("Redis setIfAbsent error: {}", e.getMessage());
            return false;
        }
    }

    public Long getTtl(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        try {
            return redisTemplate.getExpire(realKey, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis getTtl error: {}", e.getMessage());
            return null;
        }
    }

    public boolean delete(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        try {
            return Boolean.TRUE.equals(redisTemplate.delete(realKey));
        } catch (Exception e) {
            log.error("Redis delete error: {}", e.getMessage());
            return false;
        }
    }

    public boolean exists(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(realKey));
        } catch (Exception e) {
            log.error("Redis exists error: {}", e.getMessage());
            return false;
        }
    }

    public Long incr(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        try {
            return redisTemplate.opsForValue().increment(realKey);
        } catch (Exception e) {
            log.error("Redis incr error: {}", e.getMessage());
            return null;
        }
    }

    public Long decr(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        try {
            return redisTemplate.opsForValue().decrement(realKey);
        } catch (Exception e) {
            log.error("Redis decr error: {}", e.getMessage());
            return null;
        }
    }
}
