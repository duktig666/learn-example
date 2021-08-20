package cn.duktig.id.service;

import cn.duktig.id.enums.UniqueIDEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * description: 测试Redis生成唯一ID
 *
 * @author RenShiWei
 * Date: 2021/08/20 17:26
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class IRedisUniqueIDServiceTest {

    @Autowired
    private IRedisUniqueIDService redisUniqueIDService;

    /**
     * 测试生成唯一id
     */
    @Test
    public void generateUniqueId() {
        String uniqueId = redisUniqueIDService.generateUniqueId(UniqueIDEnum.TS_ORDER);
        System.out.println("唯一id：" + uniqueId);
    }

    /**
     * 多线程环境下，测试生成唯一ID
     */
    @Test
    public void generateUniqueIdForThread() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + redisUniqueIDService.generateUniqueId(UniqueIDEnum.TS_ORDER));
            }
        }, "thread-0").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + redisUniqueIDService.generateUniqueId(UniqueIDEnum.TS_ORDER));
            }
        }, "thread-1").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "分布式唯一ID:" + redisUniqueIDService.generateUniqueId(UniqueIDEnum.TS_ORDER));
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
