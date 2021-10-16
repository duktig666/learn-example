package thread.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * description:自定义同步组件（AQS原理）——互斥锁：同一个时刻只允许有一个锁占有线程
 *
 * @author RenShiWei
 * Date: 2021/7/5 21:25
 **/
public class Mutex implements Lock {

    /**
     * 静态内部类自定义AQS同步器
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 当前同步器是否处于占用状态（因为只有一个线程可以占用，所以state为1时代表占用）
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 独占式获取锁
         * 当状态为0时，说明没有线程占用；因为存在多线程并发抢占锁，所以使用CAS操作进行获取锁
         */
        @Override
        public boolean tryAcquire(int acquires) {
            if (compareAndSetState(0, 1)) {
                // 设置那个线程获取到这个锁
                super.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 释放锁，将同步状态设置为0
         * 只有一个线程可以获取到锁，释放锁时不存在并发抢占，因此不需要CAS操作
         */
        @Override
        protected boolean tryRelease(int releases) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            // 将锁的持有者置空
            setExclusiveOwnerThread(null);
            // 设置同步状态
            setState(0);
            return true;
        }

        /**
         * @return 一个Condition，每个condition都包含了一个condition队列
         */
        Condition newCondition() { return new ConditionObject(); }

    }

    /**
     * 仅需要将操作代理到Sync上即可
     */
    private final Sync sync = new Sync();

    /**
     * 获取锁
     */
    @Override
    public void lock() {
        sync.acquire(1);
    }

    /**
     * 可中断的获取锁
     *
     * @throws InterruptedException 当前线程被中断，抛出中断异常
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 非阻塞获取锁
     *
     * @return 获取失败，立即返回false
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    /**
     * 中断、非阻塞、超时 获取锁
     */
    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock() {
        sync.release(1);
    }

    /**
     * @return lock的 Condition对象
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    /**
     * 独占模式下 同步器是否被占用（一般表示，是否被当前线程所占用）
     */
    public boolean isLocked() { return sync.isHeldExclusively(); }

    /**
     * @return 等待队列上是否有线程
     */
    public boolean hasQueuedThreads() { return sync.hasQueuedThreads(); }

}

