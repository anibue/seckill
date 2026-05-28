package com.jesper.seckill.redis;

public abstract class BasePrefix implements KeyPrefix {
    
    private int expireSeconds;
    
    private String prefix;
    
    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }
    
    public BasePrefix(String prefix) {
        this(0, prefix);
    }
    
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }
    
    @Override
    public String getPrefix() {
        return getClass().getSimpleName() + ":" + prefix;
    }
}
