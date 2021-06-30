package com.leetCode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 求不重复数组 所有排列组合
 * 46. Permutations
 * 输入： nums = [1,2,3]
 * 输出： [[1,2,3]，[1,3,2]，[2,1,3]，[2,3,1]，[3,1 ,2]，[3,2,1]]
 */
public class Permute {
    public static void main(String[] args) {
        List<List<Integer>> res = new Permute().permute(new int[]{1, 2, 3, 4});
        System.out.println(res);
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), nums);
        return list;
    }

    /** 回溯法
     * @param list
     * @param tempList
     * @param nums
     */
    private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums){
        if (tempList.size() == nums.length) {
            list.add(new ArrayList<>(tempList));
            return;
        }
        for(int i = 0; i < nums.length; i++){
            if (tempList.contains(nums[i])) continue;
            tempList.add(nums[i]);
            backtrack(list, tempList, nums);
            tempList.remove(tempList.size() - 1);
        }
    }
}