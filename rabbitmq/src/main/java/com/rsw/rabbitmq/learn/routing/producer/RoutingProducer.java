package com.rsw.rabbitmq.learn.routing.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * description:rabbitmq 路由模式生产者
 * 不同消息被不同的队列订阅
 *
 * @author RenShiWei
 * Date: 2021/2/18 17:12
 **/
@Component
@RequiredArgsConstructor
public class RoutingProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * routing模式下发送消息
     * 发送时指定routingKey，由指定的消费者进行订阅
     *
     * @param routingKey 队列名
     * @param message    发送的信息
     */
    public void sendRoutingMessage(String routingKey, String message) {
        //参数exchange:交换机名称 参数routingKey:队列名称 （不使用队列，之后在消费者使用临时队列）
        rabbitTemplate.convertSendAndReceive("ex_directs", routingKey, message);
    }


}

