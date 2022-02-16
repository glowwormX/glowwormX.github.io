package com.leetCode;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        Object lock1 = new Object();
//        Object lock2 = new Object();


        new Thread(() -> {
            for (int i = 1; i < 100; i++) {
                if (i % 2 == 0) {
                    synchronized (lock1) {
                        System.out.println(i);
                        try {
                            lock1.notify();
                            lock1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        synchronized (lock1) {
            new Thread(() -> {
                for (int i = 1; i < 100; i++) {
                    if (i % 2 == 1) {
                        synchronized (lock1) {
                            System.out.println(i);
                            try {
                                lock1.notify();
                                lock1.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
            lock1.notify();
        }
        Thread.sleep(10000);
    }
}
