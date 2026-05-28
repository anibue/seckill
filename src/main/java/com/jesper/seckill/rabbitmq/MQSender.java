package com.jesper.seckill.rabbitmq;

import com.jesper.seckill.config.RabbitMQConfig;
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
        log.info("send message: {}", message);
        // 直接发送对象，由Spring AMQP Jackson消息转换器处理序列化
        amqpTemplate.convertAndSend(RabbitMQConfig.QUEUE, message);
    }
}
