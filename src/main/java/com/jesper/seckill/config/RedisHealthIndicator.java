package com.jesper.seckill.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisHealthIndicator implements HealthIndicator {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Health health() {
        try {
            String pong = redisTemplate.getConnectionFactory().getConnection().ping();
            if ("PONG".equals(pong)) {
                return Health.up().withDetail("redis", "Available").build();
            }
            return Health.down().withDetail("redis", "Not responding").build();
        } catch (Exception e) {
            log.error("Redis health check failed: {}", e.getMessage());
            return Health.down().withException(e).build();
        }
    }
}
