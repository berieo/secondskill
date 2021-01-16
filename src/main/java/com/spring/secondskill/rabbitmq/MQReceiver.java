package com.spring.secondskill.rabbitmq;

import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.service.GoodsService;
import com.spring.secondskill.service.OrderService;
import com.spring.secondskill.service.SecondsKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SecondsKillService secondsKillService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {

        log.info("receive message:"+message);
    }
}
