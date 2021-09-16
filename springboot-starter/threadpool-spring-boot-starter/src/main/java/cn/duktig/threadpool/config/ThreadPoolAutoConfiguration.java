package cn.duktig.threadpool.config;

import cn.duktig.threadpool.entity.Author;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description: 自定义配置类
 *
 * @author RenShiWei
 * Date: 2021/9/15 21:22
 **/
@Configuration
public class ThreadPoolAutoConfiguration {

    /**
     * ConditionalOnClass 需要此项目存在 ThreadPoolExecutor 类，该类为JDK自带，一定成立
     *
     * @return 线程池对象
     */
    @Bean
    @ConditionalOnClass(ThreadPoolExecutor.class)
    public ThreadPoolExecutor myThreadPool() {
        return new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }

    /**
     * 定义作者信息
     */
    @Bean
    @ConditionalOnClass(Author.class)
    public Author author() {
        return new Author("duktig", 23, "duktig666@163.com");
    }

}

