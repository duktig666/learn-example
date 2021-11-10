package cn.duktig.pubsub;

import org.springframework.stereotype.Component;

/**
 * description: 订阅者接受消息的通用接口
 *
 * @author RenShiWei
 * Date: 2021/11/10 21:34
 * blog: https://duktig.cn/
 * github知识库: https://github.com/duktig666/knowledge
 **/
@Component
public interface RedisMsg {

    /**
     * Redis订阅者接受消息的接口
     *
     * @param message 订阅的消息
     */
    void receiveMessage(String message);

}

