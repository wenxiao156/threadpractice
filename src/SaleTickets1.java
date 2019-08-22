public class SaleTickets1 extends Thread {
    Tickets1 tickets;

    public SaleTickets1(String name, Tickets1 tickets) {
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
        Tickets1 tickets = new Tickets1(15);
        int threadNum = 5;
        Thread[] threads = new SaleTickets1[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new SaleTickets1("窗口" + (i + 1), tickets);
        }
        for (int j = 0; j < threadNum; j++) {
            threads[j].start();
        }
    }
}

class Tickets1 {
    private int num;

    public Tickets1(int num) {
        this.num = num;
    }

    public synchronized int getNum() {
        return num;
    }

    public synchronized void sale() {
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









