package com.jesper.seckill.service;

import com.jesper.seckill.entity.OrderInfo;
import com.jesper.seckill.entity.SeckillOrder;
import com.jesper.seckill.entity.User;
import com.jesper.seckill.vo.GoodsVo;

public interface OrderService {
    
    SeckillOrder getOrderByUserIdGoodsId(long userId, long goodsId);
    
    OrderInfo getOrderById(long orderId);
    
    OrderInfo createOrder(User user, GoodsVo goods);
}
