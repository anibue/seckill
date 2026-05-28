package com.jesper.seckill.redis;

public class UserKey extends BasePrefix {
    
    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    public UserKey(String prefix) {
        super(prefix);
    }
    
    public static UserKey getById = new UserKey(0, "id");
    public static UserKey token = new UserKey(3600 * 24 * 2, "token");
}
