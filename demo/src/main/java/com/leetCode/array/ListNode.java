package com.leetCode.array;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    void print() {
        ListNode c = this;
        while (c != null) {
            System.out.print(c.val + ", ");
            c = c.next;
        }
        System.out.println("   print end");
    }

    public static ListNode create(int[] arr) {
        ListNode first = new ListNode(arr[0]);
        ListNode current = first;
        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            current.next = node;
            current = node;
        }
        return first;
    }
}
