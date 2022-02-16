package com.leetCode.backtrack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 求所有连续子集，给定数组无重复数据
 */
public class SubSetContinuousII {
    public static void main(String[] args) {
        Set<List<Integer>> res = new SubSetContinuousII().subsets(new int[]{1, 2, 2, 2, 2, 3});
        System.out.println(res);
    }

    private Set<List<Integer>> subsets(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        subsets(res, new ArrayList<>(), nums, 0);
        return res;
    }

    private void subsets(Set<List<Integer>> res, List<Integer> temp, int[] nums, int j) {
        if (temp.size() > 0) {
            res.add(new ArrayList<>(temp));
        }
        for (int i = j; i < nums.length; i++) {
            temp.add(nums[i]);
            subsets(res, temp, nums, i + 1);
            temp.remove(temp.size() - 1);
            if (temp.size() > 0) {
                break;
            }
        }
    }
}
