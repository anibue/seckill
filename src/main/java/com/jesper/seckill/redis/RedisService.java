package com.jesper.seckill.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            try {
                return objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                log.error("Bean to string error: {}", e.getMessage());
                return null;
            }
        }
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            try {
                return objectMapper.readValue(str, clazz);
            } catch (JsonProcessingException e) {
                log.error("String to bean error: {}", e.getMessage());
                return null;
            }
        }
    }
}
