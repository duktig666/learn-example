package cn.duktig.learn.lock;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * description:利用zooKeeper实现分布式锁测试
 *
 * @author RenShiWei
 * Date: 2021/7/24 14:05
 **/
public class DistributedLockTest {

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        //模拟两分分布式锁进行测试
        final DistributedLock lock1 = new DistributedLock();
        final DistributedLock lock2 = new DistributedLock();

        // 线程1
        new Thread(() -> {
            try {
                lock1.zkLock();
                System.out.println("线程1 启动，获取到锁");
                Thread.sleep(5 * 1000);
                lock1.unZkLock();
                System.out.println("线程1 释放锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        // 线程2
        new Thread(() -> {
            try {
                lock2.zkLock();
                System.out.println("线程2 启动，获取到锁");
                Thread.sleep(5 * 1000);
                lock2.unZkLock();
                System.out.println("线程2 释放锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
