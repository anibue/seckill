package com.jesper.seckill.redis;

public class GoodsKey extends BasePrefix {
    
    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    public GoodsKey(String prefix) {
        super(prefix);
    }
    
    public static GoodsKey getGoodsList = new GoodsKey(60, "goodsList");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "goodsDetail");
    public static GoodsKey getGoodsStock = new GoodsKey(0, "goodsStock");
}
