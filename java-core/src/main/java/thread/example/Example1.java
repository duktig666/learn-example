package thread.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * description: ABC三个线程先执行，D线程拿到ABC执行的结果后再执行。如何实现？
 *
 * @author RenShiWei
 * Date: 2021/11/24 22:12
 **/
public class Example1 {

    static class ThreadA implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName());
            return 1 + 2;
        }
    }

    static class ThreadB implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName());
            return 3 + 4;
        }
    }

    static class ThreadC implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName());
            return 5 + 6;
        }
    }

    public static void main(String[] args) {
        FutureTask<Integer> futureTaskA = new FutureTask<>(new ThreadA());
        FutureTask<Integer> futureTaskB = new FutureTask<>(new ThreadB());
        FutureTask<Integer> futureTaskC = new FutureTask<>(new ThreadC());

        new Thread(futureTaskA, "A").start();
        new Thread(futureTaskB, "B").start();
        new Thread(futureTaskC, "C").start();

        Thread threadD = new Thread(() -> {
            System.out.println("D-start");
            try {
                Integer a = futureTaskA.get();
                Integer b = futureTaskB.get();
                Integer c = futureTaskC.get();
                Integer d = a + b + c;
                System.out.println("D-end");
                System.out.println(Thread.currentThread().getName() + "-" + d);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, "D");
        threadD.start();

    }


}

