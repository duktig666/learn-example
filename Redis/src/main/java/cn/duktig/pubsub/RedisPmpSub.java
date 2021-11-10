package cn.duktig.pubsub;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/11/10 21:38
 * blog: https://duktig.cn/
 * github知识库: https://github.com/duktig666/knowledge
 **/
public class RedisPmpSub implements RedisMsg {

    /**
     * 接收消息的方法
     *
     * @param message 订阅消息
     */
    @Override
    public void receiveMessage(String message) {
        //注意通道调用的方法名要和RedisConfig2的listenerAdapter的MessageListenerAdapter参数2相同
        System.out.println("这是RedisPmpSub---" + message);
    }

}

