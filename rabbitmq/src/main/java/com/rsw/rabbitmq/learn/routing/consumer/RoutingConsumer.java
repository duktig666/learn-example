package com.rsw.rabbitmq.learn.routing.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/2/18 17:11
 **/
@Component
public class RoutingConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, //创建临时队列
                    exchange = @Exchange(value = "ex_directs", type = "direct"), //绑定交换机
                    key = {"info", "warn", "error"}
            )
    })
    public void receive1(String message) {
        System.out.println("rabbitmq-direct-1 message:" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "ex_directs", type = "direct"),
                    key = {"error"}
            )
    })
    public void receive2(String message) {
        System.out.println("rabbitmq-direct-2 message:" + message);
    }


}

