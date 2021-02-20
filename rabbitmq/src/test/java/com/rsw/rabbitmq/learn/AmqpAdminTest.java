package com.rsw.rabbitmq.learn;

import com.rsw.RabbitMqAppRun;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * description:测试AmqpAdmin管理组件的使用
 *
 * @author RenShiWei
 * Date: 2021/2/19 21:13
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitMqAppRun.class)
public class AmqpAdminTest {

    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * 测试创建direct类型的交换机
     */
    @Test
    public void createExchange() {
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        System.out.println("创建direct类型的交换机完成");
    }

    /**
     * 测试创建消息队列
     */
    @Test
    public void createQueue() {
        String queue = amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        System.out.println("创建消息队列完成");
        System.out.println(amqpAdmin.getQueueInfo(queue));
        System.out.println(amqpAdmin.getQueueProperties(queue));
    }

    /**
     * 测试添加绑定规则
     */
    @Test
    public void createBinding() {
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE, "amqpadmin.exchange",
                "info", null));
        System.out.println("创建绑定关系完成");
    }


}

