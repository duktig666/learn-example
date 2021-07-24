package cn.duktig.learn.server;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * description: 利用zookeeper实现分布式服务器
 *
 * @author RenShiWei
 * Date: 2021/7/24 13:46
 **/
public class DistributeServer {
    private static final String CONNECT_STRING = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT = 2000;
    private ZooKeeper zk = null;
    private String parentNode = "/servers";

    /**
     * 创建到 zk 的客户端连接
     */
    public void getConnect() throws IOException {
        zk = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });
    }

    /**
     * 注册服务器
     *
     * @param hostname 主机地址
     */
    public void registerServer(String hostname) throws Exception {
        String create = zk.create(parentNode + "/server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + " is online " + create);
    }

    /**
     * 业务功能
     *
     * @param hostname 主机地址
     */
    public void business(String hostname) throws Exception {
        System.out.println(hostname + " is working ...");
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        // 1 获取 zk 连接
        DistributeServer server = new DistributeServer();
        server.getConnect();
        // 2 利用 zk 连接注册服务器信息
        server.registerServer("127.0.0.1");
        // 3 启动业务功能
        server.business("127.0.0.1");
    }

}
