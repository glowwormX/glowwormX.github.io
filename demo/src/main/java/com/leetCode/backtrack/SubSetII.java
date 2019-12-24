package com.leetCode.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 求所有子集
 * 90. Subsets 有重复数据
 */
public class SubSetII {
    public static void main(String[] args) {
        List<List<Integer>> res = new SubSetII().subsets1(new int[]{5,5,5,5,5});
        System.out.println(res);
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
        list.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            //若和上一个重复 直接跳过---要求排序后
            if (i > start && nums[i] == nums[i - 1]) continue;
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

//        private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
//        list.add(new ArrayList<>(tempList));
//        Integer remove = null;
//        for(int i = start; i < nums.length; i++){
//            //上一个移除的和当前相等跳过---要求排序后
//            if (remove != null && remove == nums[i]) continue;
//            tempList.add(nums[i]);
//            backtrack(list, tempList, nums, i + 1);
//            remove = tempList.remove(tempList.size() - 1);
//        }
//    }


    //错误
    public List<List<Integer>> subsets1(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
//        Arrays.sort(nums);
        backtrack1(list, new ArrayList<>(),  nums, 0);
        return list;
    }

    private void backtrack1(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
        list.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            //移除记录中包含当前跳过---不要求排序
            if (removeListContains(tempList, nums, i)) continue;
            tempList.add(nums[i]);
            backtrack1(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }
    private boolean removeListContains(List<Integer> tempList, int[] nums, int i) {
        List<Integer> removeList = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            if (!tempList.contains(nums[j])) removeList.add(nums[j]);
        }
        return removeList.contains(nums[i]);
    }
}