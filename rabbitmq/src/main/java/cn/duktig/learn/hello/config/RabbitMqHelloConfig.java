package cn.duktig.learn.hello.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description:RabbitMq的Hello World模式的配置
 *
 * @author RenShiWei
 * Date: 2021/2/18 15:10
 **/
@Configuration
public class RabbitMqHelloConfig {

    /**
     * 实例化Hello World模式的队列，并交由Spring管理
     *
     * @return Hello World模式的队列
     */
    @Bean
    public Queue queue() {
        return new Queue("q_hello");
    }

}

