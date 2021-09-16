package cn.duktig.springboot;

import cn.duktig.threadpool.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/9/15 21:52
 **/
@SpringBootTest
public class MyStarterTest {

    @Autowired
    private Author author;

    @Autowired
    private ThreadPoolExecutor myThreadPool;

    @Test
    public void testGetThreadPoolInfo() {
        System.out.println("核心线程数" + myThreadPool.getCorePoolSize());
        System.out.println("最大线程数" + myThreadPool.getMaximumPoolSize());
    }

    @Test
    public void testGetStarterAuthorInfo() {
        System.out.println(author);
    }

}

