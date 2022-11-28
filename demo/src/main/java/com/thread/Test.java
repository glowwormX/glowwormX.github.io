package com.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

/**
 * @author xqw
 * @Description:
 * @date 2022/2/21 20:45
 */
public class Test {
//    static volatile AtomicInteger i = new AtomicInteger(0);
    static volatile int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Runnable runnable = () -> {
            synchronized (lock) {
                while (true) {
                    try {
                        lock.wait();
                        if (i >= 1000) return;
                        System.out.println(Thread.currentThread().getName() + " print: " + i);
                        i++;
                        lock.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        Thread.sleep(1000L);
        synchronized (lock) {
            lock.notify();
        }
        LockSupport.park();
    }
}
