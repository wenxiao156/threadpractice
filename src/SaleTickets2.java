public class SaleTickets2 extends Thread {
    Tickets2 tickets;

    public SaleTickets2(String name, Tickets2 tickets) {
        super(name);
        this.tickets = tickets;
    }

    @Override
    public void run() {
        while (tickets.getNum() > 0) {
            tickets.sale();
        }
    }

    public static void main(String[] args) {
        Tickets2 tickets = new Tickets2(15);
        int threadNum = 5;
        Thread[] threads = new SaleTickets2[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new SaleTickets2("窗口" + (i + 1), tickets);
        }
        for (int j = 0; j < threadNum; j++) {
            threads[j].start();
        }
    }
}

class Tickets2 {
    private int num;

    public Tickets2(int num) {
        this.num = num;
    }

    public int getNum() {
        synchronized (this) {
            return num;
        }
    }

    public void sale() {
        synchronized (this) {
            // 模拟出票，会有一定延时
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.num--;
            System.out.println(Thread.currentThread().getName() + "卖出了一张票，剩余票数为：" + this.num);
        }
    }
}




