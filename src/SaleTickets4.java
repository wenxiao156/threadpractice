public class SaleTickets4 implements Runnable {
    private int ticket = 15;// 15张代售票

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (ticket > 0) {// 还有票，继续销售
                    sale();
                } else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int windowsNum = 5;
        Thread[] windows = new Thread[windowsNum];
        SaleTickets4 tickets4 = new SaleTickets4();
        for (int i = 0; i < windowsNum; i++) {
            windows[i] = new Thread(tickets4);
            windows[i].setName("窗口" + (i + 1));
        }
        // 启动线程
        for (int i = 0; i < windowsNum; i++) {
            windows[i].start();
        }
    }

    public void sale() {
        synchronized (this) {
            try {
                // 模拟出票，会有一定延时
                Thread.sleep((long) (Math.random() * 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ticket--;
            System.out.println(Thread.currentThread().getName() + "卖出了一张票，剩余票数为：" + ticket);
        }
    }
}
