package com.jesper.seckill.constant;

public interface RedisKeyPrefix {
    
    String getPrefix();
    
    int expireSeconds();
}
