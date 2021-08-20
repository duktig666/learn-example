package thread.basic;

/**
 * description:
 * Thread类实现了Runnable的接口，线程的启动调用，可以由其他线程来做
 * 场景（线程池线程间调用）
 *
 * @author RenShiWei
 * Date: 2021/8/16 9:37
 **/
public class MyThread2 extends Thread {

    public MyThread2(Runnable target) {
        super(target);
    }

    @Override
    public void run() {
        System.out.println("MyThread2线程被调用。执行线程为：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyThread1 thread1 = new MyThread1();
        thread1.setName("thread1");
        MyThread2 thread2 = new MyThread2(thread1);
        thread2.setName("thread2");
        thread2.start();
    }

}

