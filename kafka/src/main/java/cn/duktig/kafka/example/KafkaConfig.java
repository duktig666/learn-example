package cn.duktig.kafka.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2022/2/22 22:31
 **/
@Configuration
public class KafkaConfig {

    /**
     * 新建一个异常处理器，用@Bean注入
     */
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            System.out.println("消费异常：" + message.getPayload());
            return null;
        };
    }

}

