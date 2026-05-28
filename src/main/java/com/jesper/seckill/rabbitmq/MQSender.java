package com.jesper.seckill.rabbitmq;

import com.jesper.seckill.config.RabbitMQConfig;
import com.jesper.seckill.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendSeckillMessage(SeckillMessage message) {
        String msg = RedisService.beanToString(message);
        log.info("send message: {}", msg);
        amqpTemplate.convertAndSend(RabbitMQConfig.QUEUE, msg);
    }
}
