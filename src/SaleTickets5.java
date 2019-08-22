import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTickets5 implements Runnable {
    private int tickets = 15;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        String name = Thread.currentThread().getName();//修改窗口名字
        Thread.currentThread().setName("窗口" + name.substring(name.lastIndexOf("-")));
        while (true) {
            lock.lock();
            if (tickets > 0) {
                sale();
            } else {
                break;
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int windowsNum = 5;
        BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(windowsNum);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(windowsNum, windowsNum + 1, 2, TimeUnit.MILLISECONDS, bqueue);
        Thread[] window = new Thread[windowsNum];
        SaleTickets5 tickets5 = new SaleTickets5();
        for (int i = 0; i < windowsNum; i++) {
            window[i] = new Thread(tickets5);
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
        tickets--;
        System.out.println(Thread.currentThread().getName() + "卖出了一张票，剩余票数为：" + tickets);
        lock.unlock();
    }
}
