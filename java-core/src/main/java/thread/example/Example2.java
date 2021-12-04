package thread.example;

import java.util.concurrent.Exchanger;

/**
 * description:ABC三个线程先执行，D线程拿到ABC执行的结果后再执行。如何实现？
 *
 * @author RenShiWei
 * Date: 2021/11/25 10:05
 **/
public class Example2 {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            try {
                Thread.sleep(2000);
                String a = exchanger.exchange("AA");
                System.out.println(Thread.currentThread().getName() + "-" + a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A");

        Thread threadB = new Thread(() -> {
            try {
                String b = exchanger.exchange("BB");
                System.out.println(Thread.currentThread().getName() + "-" + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B");

        Thread threadC = new Thread(() -> {
            try {
                String c = exchanger.exchange("CC");
                System.out.println(Thread.currentThread().getName() + "-" + c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C");


        Thread threadD = new Thread(() -> {
            System.out.println("D-start");
            try {
                String d = exchanger.exchange("DD");
                System.out.println("D-end");
                System.out.println(Thread.currentThread().getName() + "-" + d);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "D");

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }

}

