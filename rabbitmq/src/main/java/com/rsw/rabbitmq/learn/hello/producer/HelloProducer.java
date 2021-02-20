package com.rsw.rabbitmq.learn.hello.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * description:rabbitmq hello模式生产者
 *
 * @author RenShiWei
 * Date: 2021/2/18 15:22
 **/
@Component
@RequiredArgsConstructor
public class HelloProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Hello模式下发送消息
     */
    public void sendHelloMessage() {
        String context = "我是rabbitmq Hello模式的发送者发送的消息";
        //参数routingKey:队列名称
        rabbitTemplate.convertSendAndReceive("q_hello", context);
    }

}

