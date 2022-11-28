package com.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class NumberPrinterPark {
    static class Printer extends Thread {
        private final int printIndex, n, total;
        private Printer next;

        public Printer(int printIndex, int n, int total) {
            this.printIndex = printIndex;
            this.n = n;
            this.total = total;
        }

        public void setNext(Printer next) {
            this.next = next;
        }

        @Override
        public void run() {
            for (int i = 0, p = printIndex; p <= total; i++, p = i * n + printIndex) {
                LockSupport.park();
                System.out.println(p);
                LockSupport.unpark(next);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        main1(args);

        Printer t1 = new Printer(1, 3, 100);
        Printer t2 = new Printer(2, 3, 100);
        Printer t3 = new Printer(3, 3, 100);
        t1.setNext(t2);
        t2.setNext(t3);
        t3.setNext(t1);
        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
        LockSupport.park();
    }


    static volatile int i = 0;

    public static void main1(String[] args) {
        Printer1 t1 = new Printer1();
        Printer1 t2 = new Printer1();
        Printer1 t3 = new Printer1();
        t1.setNext(t2);
        t2.setNext(t3);
        t3.setNext(t1);
        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }

    static class Printer1 extends Thread {
        Printer1 next;

        public void setNext(Printer1 next) {
            this.next = next;
        }

        @Override
        public void run() {
            while (true) {
                LockSupport.park();
                i++;
                System.out.println(i);
                LockSupport.unpark(this.next);
            }

        }
    }
}
