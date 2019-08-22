public class SaleTickets3 extends Thread {
    private static int ticket = 15;// 15张代售票

    @Override
    public void run() {
        while (true) {
            if (ticket > 0) {// 还有票，继续销售
                sale();
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int threadNum = 5;
        Thread[] threads = new SaleTickets3[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new SaleTickets3();
            threads[i].setName("窗口" + (i + 1));
        }
        for (int j = 0; j < threadNum; j++) {
            threads[j].start();
        }
    }

    public void sale() {
        try {
            // 模拟出票，会有一定延时
            Thread.sleep((long) (Math.random() * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ticket > 0) {
            ticket--;
            System.out.println(Thread.currentThread().getName() + "卖出了一张票，剩余票数为：" + ticket);
        } else {
            System.out.println(Thread.currentThread().getName() + "想要出票，但余票为0");
        }
    }
}

