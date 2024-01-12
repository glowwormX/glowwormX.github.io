package com.leetCode.dynamicPlan;

public class Test {
    public int minDeletionsToMakeIncreasing(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int result = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1; // 至少去掉一个数，即本身
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            result = Math.max(result, dp[i]);
        }

        // 最少去掉的数等于数组长度减去最长上升子序列的长度
        return n - result;
    }

    public static void main(String[] args) {
        System.out.println(new Test().minDeletionsToMakeIncreasing(new int[]{1, 3, 4,5, 2, 3, 4, 5}));
    }
}
