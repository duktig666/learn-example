package com.rsw.rabbitmq.learn.subscribe.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description:rabbitmq 订阅消费模式消费者
 *
 * @author RenShiWei
 * Date: 2021/2/18 16:45
 **/
@Component
public class SubscribeConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, //创建临时队列
                    exchange = @Exchange(value = "ex_subscribe", type = "fanout") //绑定交换机
            )
    })
    public void receive1(Map<String, Object> map) {
        System.out.println("rabbitmq-subscribe-1 message:" + map);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "ex_subscribe", type = "fanout")
            )
    })
    public void receive2(Map<String, Object> map) {
        System.out.println("rabbitmq-subscribe-2 message:" + map);
    }

}

