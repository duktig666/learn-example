package thread.threadlocal;

import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ThreadLocal 测试
 */
public class ThreadLocalTest {

    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * 测试 斐波那契（Fibonacci）散列法
     * 斐波那契散列的非常均匀，普通散列到15个以后已经开发生碰撞。这也就是斐波那契散列的魅力，
     * 减少碰撞也就可以让数据存储的更加分散，获取数据的时间复杂度基本保持在O(1)。
     */
    @Test
    public void testFibonacciHash() {
        int hashCode = 0;
        for (int i = 0; i < 16; i++) {
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            int idx = hashCode & 15;
            System.out.println("斐波那契散列：" + idx + " 普通散列：" + (String.valueOf(i).hashCode() & 15));
        }
    }

    /**
     * 测试 ThreadLocal 每次初始化 产生新的 hashcode
     */
    @Test
    public void test_threadLocalHashCode() throws Exception {
        for (int i = 0; i < 5; i++) {
            ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
            Field threadLocalHashCode = objectThreadLocal.getClass().getDeclaredField("threadLocalHashCode");
            threadLocalHashCode.setAccessible(true);
            System.out.println("objectThreadLocal：" + threadLocalHashCode.get(objectThreadLocal));
        }
    }

    private static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 测试 线程不安全的 SimpleDateFormat
     */
    @Test
    public void testUnsafeSimpleDateFormat() {
        while (true) {
            new Thread(() -> {
                String dateStr = f.format(new Date());
                try {
                    Date parseDate = f.parse(dateStr);
                    String dateStrCheck = f.format(parseDate);
                    boolean equals = dateStr.equals(dateStrCheck);
                    if (! equals) {
                        System.out.println(equals + " " + dateStr + " " + dateStrCheck);
                    } else {
                        System.out.println(equals);
                    }
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        }
    }

    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss"));

    /**
     * 测试 线程安全的 SimpleDateFormat
     */
    @Test
    public void testSafeSimpleDateFormat() {
        while (true) {
            new Thread(() -> {
                String dateStr = threadLocal.get().format(new Date());
                try {
                    Date parseDate = threadLocal.get().parse(dateStr);
                    String dateStrCheck = threadLocal.get().format(parseDate);
                    boolean equals = dateStr.equals(dateStrCheck);
                    if (! equals) {
                        System.out.println(equals + " " + dateStr + " " + dateStrCheck);
                    } else {
                        System.out.println(equals);
                    }
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        }
    }

}
