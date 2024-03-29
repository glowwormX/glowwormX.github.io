package com.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABCUsingLockCondition {

    private int times;
    private int state;
    private static Lock lock = new ReentrantLock();
    private static Condition c1 = lock.newCondition();
    private static Condition c2 = lock.newCondition();
    private static Condition c3 = lock.newCondition();

    public PrintABCUsingLockCondition(int times) {
        this.times = times;
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        LockSupport.parkUntil(1000_000_000);
        System.out.println(System.currentTimeMillis() - l);
        PrintABCUsingLockCondition print = new PrintABCUsingLockCondition(10);
        new Thread(() -> {
            print.printLetter("A", 0, c1, c2);
        }, "A").start();
        new Thread(() -> {
            print.printLetter("B", 1, c2, c3);
        }, "B").start();
        new Thread(() -> {
            print.printLetter("C", 2, c3, c1);
        }, "C").start();
    }

    private void printLetter(String name, int targetState, Condition current, Condition next) {
        for (int i = 0; i < times; ) {
            lock.lock();
            try {
                while (state % 3 != targetState) {
                    current.await();
                }
                state++;
                i++;
                System.out.print(name);
                next.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}