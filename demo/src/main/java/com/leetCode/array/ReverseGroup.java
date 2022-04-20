package com.leetCode.array;

/**
 * 25. K 个一组翻转链表
 * 给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表。
 *
 * k是一个正整数，它的值小于或等于链表的长度。
 *
 * 如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序。
 */
public class ReverseGroup {
    public static void main(String[] args) {
        ListNode head = ListNode.create(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        head.print();
        ListNode finishHead = new Solution().reverseKGroup(head, 3);
        finishHead.print();
    }
    static class Solution {
        public ListNode reverseKGroup(ListNode head, int k) {
            ListNode preHead = null;
            ListNode preTail = null;
            ListNode currentTail = head;//1
            ListNode currentHead = reserve(currentTail, k);//3

            ListNode finishHead = currentHead;

            while(currentTail != null) {
                preHead = currentHead;//3 4
                preTail = currentTail;//1 3
                currentTail = currentTail.next;//4 n
                currentHead = reserve(currentTail, k);//6
                //上一个的尾巴指向这次的头
                preTail.next = currentHead;
            }
            return finishHead;
        }

        /**
         * 翻转链表的前k个，如果不足k个不翻转
         * @param head 链表头，返回时变成了尾，head.next变成了k的下一个
         * @param k 前k个
         * @return 翻转后的头
         */
        public ListNode reserve(ListNode head, int k) {
            if (head == null) return null;
            ListNode pre = null;
            ListNode current = head;
            int i = 0;
            ListNode next = null;
            while(current != null && i < k) {
                next = current.next;
                current.next = pre;  //null 1 2
                pre = current;          //1 2 3
                current = next;         //2 3 4
                i++;                    //1 2 3
            }
            head.next = next;
            if (i < k) {
                return reserve(pre, i);
            }
            return pre;
        }
    }
}
