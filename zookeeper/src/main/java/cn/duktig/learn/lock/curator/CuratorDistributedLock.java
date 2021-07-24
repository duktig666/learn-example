package cn.duktig.learn.lock.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * description:Curator框架实现分布式锁
 *
 * @author RenShiWei
 * Date: 2021/7/24 14:24
 **/
public class CuratorDistributedLock {

    public static void main(String[] args) {

        // 创建分布式锁1
        InterProcessMutex lock1 = new InterProcessMutex(getCuratorFramework(), "/locks");

        // 创建分布式锁2
        InterProcessMutex lock2 = new InterProcessMutex(getCuratorFramework(), "/locks");

        new Thread(() -> {
            try {
                lock1.acquire();
                System.out.println("线程1 获取到锁");

                lock1.acquire();
                System.out.println("线程1 再次获取到锁");

                Thread.sleep(5 * 1000);

                lock1.release();
                System.out.println("线程1 释放锁");

                lock1.release();
                System.out.println("线程1  再次释放锁");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                lock2.acquire();
                System.out.println("线程2 获取到锁");

                lock2.acquire();
                System.out.println("线程2 再次获取到锁");

                Thread.sleep(5 * 1000);

                lock2.release();
                System.out.println("线程2 释放锁");

                lock2.release();
                System.out.println("线程2  再次释放锁");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static CuratorFramework getCuratorFramework() {

        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(3000, 3);

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .connectionTimeoutMs(2000)
                .sessionTimeoutMs(2000)
                .retryPolicy(policy).build();

        // 启动客户端
        client.start();

        System.out.println("zookeeper 启动成功");
        return client;
    }

}

