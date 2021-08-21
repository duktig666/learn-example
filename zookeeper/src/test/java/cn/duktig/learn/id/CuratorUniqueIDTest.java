package cn.duktig.learn.id;

import org.junit.Test;

public class CuratorUniqueIDTest {

    /**
     * 测试 Curator 生成分布式id
     */
    @Test
    public void generateId() {
        String id = CuratorUniqueID.generateId();
        System.out.println(id);
    }

    /**
     * 测试 Curator 生成唯一ID（多线程下）
     */
    @Test
    public void getUniqueIdForMore() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + CuratorUniqueID.generateId());
            }
        }, "thread-0").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + CuratorUniqueID.generateId());
            }
        }, "thread-1").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + CuratorUniqueID.generateId());
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
