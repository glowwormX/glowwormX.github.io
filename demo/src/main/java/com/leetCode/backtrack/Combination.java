package com.leetCode.backtrack;

import java.util.ArrayList;
import java.util.List;

public class Combination {
    public static void main(String[] args) {
        System.out.println(new Combination().combine(4, 2));
        /*
        输出
        [
          [2,4]，
          [3,4]，
          [2,3]，
          [1,2]，
          [1,3]，
          [1,4]，
        ]
        */
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, new ArrayList<>(), n, k, 1);
        return res;
    }

    public void backtrack(List<List<Integer>> res, List<Integer> temp, int n, int k, int start) {
        if (temp.size() == k) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i <= n; i++) {
            temp.add(i);
            backtrack(res, temp, n, k, i + 1);
            temp.remove(temp.size() - 1);
        }
    }
}
