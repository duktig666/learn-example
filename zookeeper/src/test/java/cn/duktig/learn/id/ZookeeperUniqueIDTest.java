package cn.duktig.learn.id;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

public class ZookeeperUniqueIDTest {

    ZookeeperUniqueID zookeeperUniqueID = new ZookeeperUniqueID();

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

    /**
     * 单机ZooKeeper
     * 线程池开10个线程生成10W个 分布式ID 测速
     * 大约为：106959 ms
     */
    @Test
    public void generateUniqueIdForThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = null;

        //创建线程池
        threadPoolExecutor = new ThreadPoolExecutor(10,
                20,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(20),
                new ThreadPoolExecutor.CallerRunsPolicy());
        LocalDateTime startTime = LocalDateTime.now();
        for (int i = 0; i < 100000; i++) {
            FutureTask<String> futureTask = new FutureTask<>(new CuratorUniqueIDTest.ThreadPoolTask());
            threadPoolExecutor.execute(futureTask);
            try {
                String id = futureTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPoolExecutor.shutdown();
        LocalDateTime endTime = LocalDateTime.now();
        // 计算时间差值
        long minutes = Duration.between(startTime, endTime).toMillis();
        // 输出
        System.out.println("线程池 生成10万个分布式id所用的时间：" + minutes + " ms");

    }

    class ThreadPoolTask implements Callable<String> {

        @Override
        public String call() {
            String id = zookeeperUniqueID.getUniqueId();
            System.out.println(Thread.currentThread().getName() + "---" + id);
            return id;
        }

    }

}
