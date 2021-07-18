package cn.duktig.learn.work.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * description:测试rabbitmq work模式生产消息（均衡消费）
 * 模拟10条消息被两个消费者消费
 *
 * @author RenShiWei
 * Date: 2021/2/18 16:21
 **/
@Component
@RequiredArgsConstructor
public class WorkProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Work模式下发送消息
     */
    public void sendWorkMessage() {
        String context;
        for (int i = 0; i < 10; i++) {
            context = "我是rabbitmq work模式的发送者" + i + "发送的消息——" + i;
            //参数routingKey:队列名称
            rabbitTemplate.convertSendAndReceive("q_work", context);
        }
    }

}

