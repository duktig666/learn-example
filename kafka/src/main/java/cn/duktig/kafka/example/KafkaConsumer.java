package cn.duktig.kafka.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * description: 消费者
 *
 * @author RenShiWei
 * Date: 2022/2/22 21:48
 **/
@Component
public class KafkaConsumer {

    /**
     * kafka的监听器，topic为"kafkaTest"，消费者组为"kafkaTestGroup"
     */
    @KafkaListener(topics = "kafkaTest", groupId = "kafkaTestGroup", errorHandler = "consumerAwareErrorHandler")
    public void listenKafkaTestGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println("收到消息：" + value);
        System.out.println(record);
        //手动提交offset
        ack.acknowledge();
        // 测试异常处理
//        throw new RuntimeException("简单消费-模拟异常");
    }

    /*//配置多个消费组
    @KafkaListener(topics = "kafkaTest",groupId = "kafkaTestGroup2")
    public void listenTulingGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        ack.acknowledge();
    }*/

}

