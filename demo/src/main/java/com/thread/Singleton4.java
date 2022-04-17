package com.thread;

public class Singleton4 {
    private static class Inner {
        private static final Singleton4 singleton = new Singleton4();
    }

    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        return Inner.singleton;
    }
}