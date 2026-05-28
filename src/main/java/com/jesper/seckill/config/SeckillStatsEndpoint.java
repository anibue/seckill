package com.jesper.seckill.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "seckill-stats")
@Slf4j
public class SeckillStatsEndpoint {

    @ReadOperation
    public Map<String, Object> stats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("timestamp", System.currentTimeMillis());
        stats.put("status", "running");
        stats.put("version", "1.0.0");
        return stats;
    }
}
