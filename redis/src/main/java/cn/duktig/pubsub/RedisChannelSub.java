package cn.duktig.pubsub;

/**
 * description: Redis订阅者
 *
 * @author RenShiWei
 * Date: 2021/11/10 21:26
 * blog: https://duktig.cn/
 * github知识库: https://github.com/duktig666/knowledge
 **/
public class RedisChannelSub implements RedisMsg {

    @Override
    public void receiveMessage(String message) {
        //注意通道调用的方法名要和 RedisPubSubConfig 的listenerAdapter的 MessageListenerAdapter 参数2相同
        System.out.println("这是RedisChannelSub" + "-----" + message);
    }

}

