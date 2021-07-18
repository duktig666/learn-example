package cn.duktig.learn;

import cn.duktig.RabbitMqAppRun;
import cn.duktig.learn.hello.producer.HelloProducer;
import cn.duktig.learn.routing.producer.RoutingProducer;
import cn.duktig.learn.subscribe.producer.SubscribeProducer;
import cn.duktig.learn.topic.producer.TopicProducer;
import cn.duktig.learn.work.producer.WorkProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * description:测试rabbitmq的消息发送
 *
 * @author RenShiWei
 * Date: 2021/2/18 16:14
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitMqAppRun.class)
public class RabbitMqSendMessageTest {

    @Autowired
    private HelloProducer helloProducer;
    @Autowired
    private WorkProducer workProducer;
    @Autowired
    private SubscribeProducer subscribeProducer;
    @Autowired
    private RoutingProducer routingProducer;
    @Autowired
    private TopicProducer topicProducer;

    /**
     * 测试Hello模式发送消息
     * 发送的消息内容在生产者固定，在接下来的模式当中可以尝试传递指定类型的消息
     */
    @Test
    public void testHelloSend() {
        helloProducer.sendHelloMessage();
    }

    /**
     * 测试Work模式下发送消息
     * 发送消息内容固定，模拟生产10条消息，2个消费者均衡接收
     * 经测试，默认消费者消费消息的时候有默认的延迟时间（可以在一定程度上做到流量削峰，但是这个延迟时间应该是可以配置的）
     */
    @Test
    public void testWorkSend() {
        workProducer.sendWorkMessage();
    }

    /**
     * 测试Publish/Subscribe模式发布消息
     * 这次发送map类型的消息
     * 生产者生产一条消息，订阅了的消费者都接受到这条消息
     */
    @Test
    public void testSubscribeSend() {
        Map<String, Object> map = new HashMap<>();
        map.put("producer", "rabbitmq-Subscribe");
        map.put("message", "我是来自rabbitmq的订阅消费模式的消息");
        subscribeProducer.sendSubscribeMessage(map);
    }

    /**
     * 测试Routing模式发布消息
     * 发送消息时指定发送到的队列，只有满足队列的消费者才可以接收到
     * 生产者生产一条消息，只有指定了当前队列的消费者才可以消费
     */
    @Test
    public void testRoutingSend() {
        routingProducer.sendRoutingMessage("info", "Routing模式发送了一个info的消息");
        routingProducer.sendRoutingMessage("error", "Routing模式发送了一个error的消息");
    }

    /**
     * 测试Topic模式发布消息
     * 发送消息时指定发送到的队列，只有满足队列的消费者才可以接收到（区别于Routing模式的是，可以通过正则匹配到指定的队列）
     * 生产者生产一条消息，只有指定了当前队列的消费者才可以消费
     */
    @Test
    public void testTopicSend() {
        topicProducer.sendTopicMessage("user.save", "用户1");
        topicProducer.sendTopicMessage("produce.phone.xiaomi", "小米11");
    }


}

