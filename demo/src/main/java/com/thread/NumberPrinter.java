package com.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class NumberPrinter {
    static class Printer extends Thread {
        private final int printIndex, n, total;
        private final ReentrantLock lock;
        private final Condition current;
        private Condition next;

        public Printer(int printIndex, int n, int total, ReentrantLock lock) {
            this.printIndex = printIndex;
            this.n = n;
            this.total = total;
            this.lock = lock;
            this.current = lock.newCondition();
        }

        public Condition getCondition() {
            return current;
        }

        public void setNext(Condition next) {
            this.next = next;
        }

        @Override
        public void run() {
            for (int i = 0, p = printIndex; p <= total; i++, p = i * n + printIndex) {
                lock.lock();
                try {
                    current.await();
                    System.out.println(p);
                    next.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Printer t1 = new Printer(1, 3, 100, lock);
        Printer t2 = new Printer(2, 3, 100, lock);
        Printer t3 = new Printer(3, 3, 100, lock);
        t1.setNext(t2.getCondition());
        t2.setNext(t3.getCondition());
        t3.setNext(t1.getCondition());
        t1.setName("print1");
        t2.setName("print2");
        t3.setName("print3");
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(1000);
        //加锁才能调用signal方法
        //先唤醒线程1
        lock.lock();
        try {
            t1.getCondition().signal();
        } finally {
            lock.unlock();
        }
        LockSupport.park();
    }
}
