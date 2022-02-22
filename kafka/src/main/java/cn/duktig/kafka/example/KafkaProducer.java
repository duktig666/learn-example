package cn.duktig.kafka.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

    @RequestMapping("/send")
    public void send() {
        //发送功能就一行代码~
        kafkaTemplate.send(TOPIC_NAME, "key", "test message send. TIME：" + LocalDateTime.now());
    }

}

