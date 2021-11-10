package cn.duktig.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * description:定时发布消息
 *
 * @author RenShiWei
 * Date: 2021/11/10 21:39
 * blog: https://duktig.cn/
 * github知识库: https://github.com/duktig666/knowledge
 **/
@EnableScheduling
@Component
public class TestScheduleRedisPublishController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向redis消息队列index通道发布消息
     */
    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        stringRedisTemplate.convertAndSend("pmp", String.valueOf(Math.random()));
        stringRedisTemplate.convertAndSend("channel", String.valueOf(Math.random()));
    }

}

