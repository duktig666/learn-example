package cn.duktig.learn.server;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * description:利用zookeeper实现分布式服务器的客户端
 *
 * @author RenShiWei
 * Date: 2021/7/24 13:57
 **/
public class DistributeClient {

    private ZooKeeper zk = null;

    /**
     * 创建到 zk 的客户端连接
     */
    public void getConnect() throws IOException {
        String connectString = "127.0.0.1:2181";
        int sessionTimeout = 2000;
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 再次启动监听
                try {
                    getServerList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取服务器列表信息
     */
    public void getServerList() throws Exception {
        // 1 获取服务器子节点信息，并且对父节点进行监听
        String parentNode = "/servers";
        List<String> children = zk.getChildren(parentNode, true);
        // 2 存储服务器信息列表
        ArrayList<String> servers = new ArrayList<>();
        // 3 遍历所有节点，获取节点中的主机名称信息
        for (String child : children) {
            byte[] data = zk.getData(parentNode + "/" + child,
                    false, null);
            servers.add(new String(data));
        }
        // 4 打印服务器列表信息
        System.out.println(servers);
    }

    /**
     * 业务功能
     */
    public void business() throws Exception {
        System.out.println("client is working ...");
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        // 1 获取 zk 连接
        DistributeClient client = new DistributeClient();
        client.getConnect();
        // 2 获取 servers 的子节点信息，从中获取服务器信息列表
        client.getServerList();
        // 3 业务进程启动
        client.business();
    }
}

