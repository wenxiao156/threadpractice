import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SaleTickets6 implements Runnable {

    private int tickets = 15;
    private ReadWriteLock lock = new ReentrantReadWriteLock(false);

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName("窗口" + name.substring(name.lastIndexOf("-")));
        while (true) {
            lock.writeLock().lock();
            if (tickets > 0) {
                try {
                    // 模拟出票，会有一定延时
                    Thread.sleep((long) (Math.random() * 100));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tickets--;
                System.out.println(Thread.currentThread().getName() + "卖出了一张票，剩余票数为：" + tickets);
            } else {
                lock.writeLock().unlock();
                break;
            }
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        int windowsNum = 5;
        ExecutorService pool = Executors.newCachedThreadPool();
        Thread[] window = new Thread[windowsNum];
        SaleTickets6 tickets6 = new SaleTickets6();
        for (int i = 0; i < windowsNum; i++) {
            window[i] = new Thread(tickets6);
        }
        for (int i = 0; i < windowsNum; i++) {
            pool.execute(window[i]);
        }
        pool.shutdown();
    }
}
