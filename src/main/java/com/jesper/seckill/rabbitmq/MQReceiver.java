package com.jesper.seckill.rabbitmq;

import com.jesper.seckill.config.RabbitMQConfig;
import com.jesper.seckill.entity.SeckillOrder;
import com.jesper.seckill.entity.User;
import com.jesper.seckill.redis.RedisService;
import com.jesper.seckill.service.GoodsService;
import com.jesper.seckill.service.OrderService;
import com.jesper.seckill.service.SeckillService;
import com.jesper.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receive(SeckillMessage message) {
        log.info("receive message: {}", message);
        User user = message.getUser();
        long goodsId = message.getGoodsId();

        // Redis预减库存已保护，无需再次检查库存
        // 直接查询商品信息用于下单
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (goodsVo == null) {
            return;
        }

        // 判断重复秒杀
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }

        // 减库存 下订单 写入秒杀订单
        seckillService.seckill(user, goodsVo);
    }

    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message: {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message: {}", message);
    }
}
