package com.rsw.rabbitmq.learn.hello.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * description:rabbitmq hello模式消费者
 *
 * @author RenShiWei
 * Date: 2021/2/18 15:47
 **/
@Component
@RabbitListener(queuesToDeclare = @Queue("q_hello"))
public class HelloConsumer {

    @RabbitHandler
    public void receive(String message) {
        System.out.println("rabbitmq-Hello message:" + message);
    }


}

