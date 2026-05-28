package com.jesper.seckill.redis;

public class SeckillKey extends BasePrefix {
    
    public SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    public SeckillKey(String prefix) {
        super(prefix);
    }
    
    public static SeckillKey isGoodsOver = new SeckillKey(0, "goodsOver");
}
