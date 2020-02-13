package com.leetCode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 求所有子集，给定数组无重复数据
 * 78. Subsets
 */
public class SubSet {
    public static void main(String[] args) {
        List<List<Integer>> res = new SubSet().subsets(new int[]{2,4,3,1});
        System.out.println(res);
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    /** 回溯法
     * @param list 结果
     * @param tempList 回溯的当前list
     * @param nums 程序输入
     * @param start 开始的索引
     */
    private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }
}
