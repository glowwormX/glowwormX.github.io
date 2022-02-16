package com.leetCode.backtrack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 求所有连续子集，给定数组无重复数据
 */
public class SubSetContinuous {
    public static void main(String[] args) {
        List<List<Integer>> res = new SubSetContinuous().subsets(new int[]{1, 2, 3, 4});
//        List<List<Integer>> res = new SubSetContinuous().subsets(new int[]{1,2,2,2,2,3});
        System.out.println(res);
    }

    private List<List<Integer>> subsets(int[] nums) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        subsets(res, new ArrayList<>(), nums, 0);
        return res;
    }

    private void subsets(List<List<Integer>> res, List<Integer> temp, int[] nums, int j) {
        if (temp.size() > 0) {
            res.add(new ArrayList<>(temp));
        }
        for (int i = j; i < nums.length; i++) {
            temp.add(nums[i]);
            subsets(res, temp, nums, i + 1);
            temp.remove(temp.size() - 1);
            //连续子集判断
            if (temp.size() > 0) {
                break;
            }
        }
    }
}
