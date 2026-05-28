package com.jesper.seckill.mapper;

import com.jesper.seckill.entity.OrderInfo;
import com.jesper.seckill.entity.SeckillOrder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    @Insert("insert into sk_order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date) values(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OrderInfo orderInfo);

    @Insert("insert into sk_order(user_id, order_id, goods_id) values(#{userId}, #{orderId}, #{goodsId})")
    int insertSeckillOrder(SeckillOrder seckillOrder);

    @Select("select * from sk_order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);

    @Select("select * from sk_order where user_id = #{userId} and goods_id = #{goodsId}")
    SeckillOrder getSeckillOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);
}
