package com.jesper.seckill.redis;

public interface KeyPrefix {
    
    int expireSeconds();
    
    String getPrefix();
}
