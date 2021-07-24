package cn.duktig.learn.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * description:测试ZooKeeper的API
 *
 * @author RenShiWei
 * Date: 2021/7/24 11:26
 **/
public class ZooKeeperApi {

    private static final String CONNECT_STRING = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT = 2000;
    private ZooKeeper zkClient = null;

    /**
     * 连接到zookeeper
     */
    @Before
    public void init() throws Exception {
        zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 收到事件通知后的回调函数（用户的业务逻辑）
                System.out.println(watchedEvent.getType() + "--" + watchedEvent.getPath());
                // 再次启动监听
                try {
                    List<String> children = zkClient.getChildren("/", true);
                    for (String child : children) {
                        System.out.println(child);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建子节点
     */
    @Test
    public void create() throws Exception {
        // 参数 1：要创建的节点的路径； 参数 2：节点数据 ； 参数 3：节点权限 ；参数 4：节点的类型
        String nodeCreated = zkClient.create("/duktig", "rsw".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    /**
     * 获取子节点
     */
    @Test
    public void getChildren() throws Exception {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 判断 znode 是否存在
     */
    @Test
    public void exist() throws Exception {
        Stat stat = zkClient.exists("/duktig", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }

}


