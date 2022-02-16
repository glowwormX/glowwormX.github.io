package com.leetCode.lincked;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheTest {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 1);
        cache.put(2, 3);
        cache.put(4, 1);
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
    }

    static class LRUCache {
        Map<Integer, Entry> values;
        int capacity;

        Entry head;
        Entry tail;

        public LRUCache(int capacity) {
            this.values = new HashMap<>(capacity);
            this.capacity = capacity;
        }

        public int get(int key) {
            Entry e = values.get(key);
            if (e == null) return -1;
            if (head == e) return e.value;

            e.pre.next = e.next;
            if (tail == e) {
                tail = e.pre;
            } else {
                e.next.pre = e.pre;
            }

            //toHead
            e.next = head;
            if (head != null) {
                head.pre = e;
            }
            head = e;
            return e.value;
        }

        public void put(int key, int value) {
            Entry e = new Entry();
            e.key = key;
            e.value = value;

            if (values.size() == capacity) {
                Entry last = values.get(key);
                if (last != null) {
                    deleteKey(last);
                } else {
                    removeTail();
                }
            }

            //addToHead
            addToHead(key, e);
        }

        private void addToHead(int key, Entry e) {
            e.next = head;
            if (head != null) {
                head.pre = e;
            }
            head = e;

            if (tail == null) {
                tail = e;
            }
            values.put(key, e);
        }

        private void removeTail() {
            values.remove(tail.key);
            if (tail.pre != null) {
                tail.pre.next = null;
            }
            tail = tail.pre;
        }

        private void deleteKey(Entry last) {
            values.remove(last.key);
            if (last.pre != null) {
                last.pre.next = last.next;
            } else {
                head = last.next;
            }
            if (last.next != null) {
                last.next.pre = last.pre;
            } else {
                tail = last.pre;
            }
        }

        class Entry {
            int key;
            int value;
            Entry next;
            Entry pre;
        }
    }

}