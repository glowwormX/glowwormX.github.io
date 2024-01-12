package com.leetCode.array;

/**
 * 80 有序数组删除大于两项的值，剩余多少个
 */
public class RemoveDuplicates {
    public static void main(String[] args) {
        int res = new RemoveDuplicates().removeDuplicates(new int[]{1, 1, 2, 2, 2, 3, 4});
        System.out.println(res);
    }
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n <= 2) {
            return n;
        }
        int slow = 2, fast = 2;
        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }
}
