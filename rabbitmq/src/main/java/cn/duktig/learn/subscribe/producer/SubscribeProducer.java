package cn.duktig.learn.subscribe.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description:rabbitmq 订阅消费模式生产者
 * 开始使用交换机的概念
 *
 * @author RenShiWei
 * Date: 2021/2/18 16:45
 **/
@Component
@RequiredArgsConstructor
public class SubscribeProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Subscribe模式下发送消息
     * 模拟发送一个消息，多个消费者都可以接收
     * 这次模拟发送map类型消息
     */
    public void sendSubscribeMessage(Map<String, Object> map) {
        //参数exchange:交换机名称 参数routingKey:队列名称 （不使用队列，之后在消费者使用临时队列）
        rabbitTemplate.convertSendAndReceive("ex_subscribe", "", map);
    }

}

