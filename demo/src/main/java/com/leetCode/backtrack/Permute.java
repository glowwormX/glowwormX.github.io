package com.leetCode.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permute {
    public static void main(String[] args) {
        List<List<Integer>> res = new Permute().permute(new int[]{1, 2, 3, 4});
        System.out.println(res);
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++){
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }
}