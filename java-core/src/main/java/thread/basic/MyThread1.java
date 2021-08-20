package thread.basic;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/8/16 9:36
 **/
public class MyThread1 extends Thread {

    @Override
    public void run() {
        System.out.println("MyThread1线程被调用。执行线程为：" + Thread.currentThread().getName());
    }

}

