package com.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class NumberPrinter1 {
    static class Printer extends Thread {
        private final int printIndex, total;
        private final Object lock;

        public Printer(int printIndex, int total, Object lock) {
            this.printIndex = printIndex;
            this.total = total;
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0, p = printIndex; p <= total; i++, p = i * 2 + printIndex) {
                synchronized (lock) {
                    try {
                        lock.wait();
                        System.out.println(p);
                        lock.notify();
//                        notify后另一个线程被唤醒，但是还没出synchronized代码块，进入blocked状态
//                        while (true) {
//                            System.out.println(printState.getState());
//                            Thread.sleep(100);
//                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    static Thread printState;
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Printer t1 = new Printer(1, 100, lock);
        Printer t2 = new Printer(2, 100, lock);
        printState = t2;
        t1.setName("print1");
        t2.setName("print2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        synchronized (lock) {
            lock.notify();
        }
        LockSupport.park();
    }

}
