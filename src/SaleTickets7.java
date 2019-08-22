import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTickets7 implements Runnable{
    private static AtomicInteger tickets = new AtomicInteger(15);
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName("窗口" + name.substring(name.lastIndexOf("-")));//修改线程名字
        while (true) {
            lock.lock();
            if (tickets.get() > 0) {
                sale();
            } else {
                lock.unlock();
                break;
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int windowsNum = 5;
        BlockingQueue<Runnable> bqueue=new ArrayBlockingQueue<Runnable>(windowsNum);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(windowsNum, windowsNum + 1, 2, TimeUnit.MILLISECONDS, bqueue);
        Thread[] window = new Thread[windowsNum];
        SaleTickets7 tickets7 = new SaleTickets7();
        for (int i = 0; i < windowsNum; i++) {
            window[i] = new Thread(tickets7);
        }
        for (int i = 0; i < windowsNum; i++) {
            pool.execute(window[i]);
        }
        pool.shutdown();
    }

    private void sale() {
        lock.lock();
        try {
            // 模拟出票，会有一定延时
            Thread.sleep((long) (Math.random() * 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "卖出了一张票，剩余票数为：" + tickets.addAndGet(-1));
        lock.unlock();
    }
}