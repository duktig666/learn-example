package cn.duktig.learn.id;

import org.junit.Test;

public class ZookeeperUniqueIDTest {

    /**
     * 测试 zk 生成唯一ID
     */
    @Test
    public void getUniqueId() {
        ZookeeperUniqueID zookeeperUniqueID = new ZookeeperUniqueID();
        for (int i = 0; i < 10; i++) {
            System.out.println("分布式唯一ID:" + zookeeperUniqueID.getUniqueId());
        }
    }

    /**
     * 测试 zk 生成唯一ID（多线程下）
     */
    @Test
    public void getUniqueIdForMore() {
        ZookeeperUniqueID zookeeperUniqueID = new ZookeeperUniqueID();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + zookeeperUniqueID.getUniqueId());
            }
        }, "thread-0").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + zookeeperUniqueID.getUniqueId());
            }
        }, "thread-1").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + zookeeperUniqueID.getUniqueId());
            }
        }, "thread-2").start();

        //睡眠，用以保证有充足的时间执行
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
