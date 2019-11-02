package com.leetCode.array;

import java.util.Arrays;

/**
 * @author 徐其伟
 * @Description: 求数组中三个值的和最接近目标值的值
 * 16. 3Sum Closest
 * 类似问题 2sum 3sum
 * @date 19-2-27 下午2:44
 */
public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        int result = 0, abs = Integer.MAX_VALUE;
        //先排序
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i < len - 2; i++) {
            int left = i + 1;
            int right = len - 1;
            while (left < right) {
                int sum = nums[left] + nums[right] + nums[i];
                if (sum == target) {
                    return target;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
                if (Math.abs(sum - target) < abs) {
                    abs = Math.abs(sum - target);
                    result = sum;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new ThreeSumClosest().threeSumClosest(new int[]{1, 1, 1, 0}, 100));
    }
}
