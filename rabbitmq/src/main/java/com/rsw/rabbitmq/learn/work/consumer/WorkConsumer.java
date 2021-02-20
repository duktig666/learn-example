package com.rsw.rabbitmq.learn.work.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/2/18 16:21
 **/
@Component
public class WorkConsumer {

    @RabbitListener(queuesToDeclare = @Queue("q_work"))
    public void receive1(String message) {
        System.out.println("rabbitmq-Work-1 message:" + message);
    }

    @RabbitListener(queuesToDeclare = @Queue("q_work"))
    public void receive2(String message) {
        System.out.println("rabbitmq-Work-2 message:" + message);
    }

}

