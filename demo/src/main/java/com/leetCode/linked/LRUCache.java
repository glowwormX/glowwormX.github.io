package com.leetCode.linked;

import java.util.HashMap;

public class LRUCache {
    HashMap<Integer, Entry> map;
    int capacity;
    Entry head;
    Entry tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Entry(-1, -1, null, null);
        this.tail = new Entry(-1, -1, head, null);
        this.head.next = tail;
    }

    //获取entry 存在则移动到头
    public int get(int key) {
        Entry e = map.get(key);
        if (e == null) {
            return -1;
        }
        delete(e);
        addToHead(e);
        return e.value;
    }

    private void delete(Entry e) {
        e.pre.next = e.next;
        e.next.pre = e.pre;
    }
    private void addToHead(Entry e) {
        e.next = head.next;
        head.next.pre = e;
        head.next = e;
        e.pre = head;
    }

    //若key存在，更改数据，移动到头
    //若key不存在，插入数据到头，判断容量是否需要删除队尾
    public void put(int key, int value) {
        Entry e = map.get(key);
        if (e != null) {
            e.value = value;
            delete(e);
            addToHead(e);
        } else {
            e = new Entry(key, value, null, null);
            addToHead(e);
            map.put(key, e);

            if (map.size() > capacity) {
                int d = tail.pre.key;
                delete(tail.pre);
                map.remove(d);
            }
        }
    }

    class Entry {
        int key;
        int value;
        Entry pre;
        Entry next;

        public Entry(int key, int value, Entry pre, Entry next) {
            this.key = key;
            this.value = value;
            this.pre = pre;
            this.next = next;
        }
    }
}



/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */