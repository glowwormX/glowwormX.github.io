package com.thread;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xqw
 * @description:
 * @date 2020/10/24
 */
public class ReadWriteLockMap<K, V> {
    private HashMap<K, V> map = new HashMap<>();
    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public V get(K key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public V put(K key, V value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}
