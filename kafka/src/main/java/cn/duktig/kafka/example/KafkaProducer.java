package cn.duktig.kafka.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * description: 生产者
 *
 * @author RenShiWei
 * Date: 2022/2/22 21:44
 **/
@RestController
public class KafkaProducer {

    /** topic的名称 */
    private final static String TOPIC_NAME = "kafkaTest";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 简单的发送消息
     */
    @RequestMapping("/send")
    public void send() {
        //发送功能就一行代码~
        kafkaTemplate.send(TOPIC_NAME, "key", "test message send. TIME：" + LocalDateTime.now());
    }

    /**
     * 带回调的发送消息
     */
    @GetMapping("/kafka/callbackOne/{message}")
    public void send2(@PathVariable("message") String callbackMessage) {
        kafkaTemplate.send(TOPIC_NAME, callbackMessage).addCallback(success -> {
            // 消息发送到的topic
            String topic = Objects.requireNonNull(success).getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
        }, failure -> {
            System.out.println("发送消息失败:" + failure.getMessage());
        });
    }

    /**
     * 不带 事务的发送消息；异常发送成功
     */
    @GetMapping("/kafka/transaction/success")
    public void send3() {
        // 不声明事务：后面报错但前面消息已经发送成功了
        kafkaTemplate.send(TOPIC_NAME, "test executeInTransaction");
        throw new RuntimeException("fail");
    }

    /**
     * 带 事务的发送消息；异常发送失败
     */
    @GetMapping("/kafka/transaction/fail")
    public void send4() {
        // 声明事务：后面报错消息不会发出去
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(TOPIC_NAME, "test executeInTransaction");
            throw new RuntimeException("fail");
        });
    }
    

}

