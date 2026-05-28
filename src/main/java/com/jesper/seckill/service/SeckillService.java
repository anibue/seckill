package com.jesper.seckill.service;

import com.jesper.seckill.entity.OrderInfo;
import com.jesper.seckill.entity.User;
import com.jesper.seckill.vo.GoodsVo;

public interface SeckillService {
    
    OrderInfo seckill(User user, GoodsVo goods);
    
    long getSeckillResult(long userId, long goodsId);
}
