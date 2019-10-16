package com.leetCode.array;

/**
 * 23. Merge k Sorted Lists
 */
public class MergeKLists {
    public static void main(String[] args) {
//        new MergeKLists().mergeKLists();
    }
    //N个元素，k行，时间O(kN)，空间O(1)
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode res = null;
        ListNode current = null;
        boolean loop = true;
        while (loop) {
            int min = Integer.MAX_VALUE;
            int min_index = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null && lists[i].val < min) {
                    min = lists[i].val;
                    min_index = i;
                }
            }
            if (min_index == -1) break;
            if (current == null) {
                current = lists[min_index];
                res = current;
            } else {
                current.next = lists[min_index];
                current = current.next;
            }
            lists[min_index] = lists[min_index].next;
        }
        return res;
    }
}

