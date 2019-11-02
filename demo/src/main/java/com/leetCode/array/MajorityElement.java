package com.leetCode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐其伟
 * @Description:
 * order 69
 * 查找主要元素，超过n/2或3/2的
 * @date 2019/6/7 17:40
 */
public class MajorityElement {
    public static void main(String[] args) {
        System.out.println(new MajorityElement().majorityElement2n(new int[]{2, 2, 3, 1}));
        System.out.println(new MajorityElement().majorityElement3n(new int[]{3, 1, 1, 3, 2}));
    }

    /**
     * nums中超过n/2的数
     *
     * @param nums
     * @return
     */
    public int majorityElement2n(int[] nums) {
        int tmp = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == tmp) {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                tmp = nums[i];
                count = 1;
            }
        }
        return tmp;
    }

    /**
     * 超过n/3的数
     *
     * @param nums
     * @return
     */
    public List<Integer> majorityElement3n(int[] nums) {
        Integer tmp1 = null;
        Integer tmp2 = null;
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (tmp1 != null && nums[i] == tmp1) {
                count1++;
            } else if (tmp2 != null && nums[i] == tmp2) {
                count2++;
            } else if (count1 == 0) {
                tmp1 = nums[i];
                count1 = 1;
            } else if (count2 == 0) {
                tmp2 = nums[i];
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;

        for (int i = 0; i < nums.length; i++) {
            if (tmp1 != null && nums[i] == tmp1) count1++;
            if (tmp2 != null && nums[i] == tmp2) count2++;
        }

        List<Integer> res = new ArrayList<Integer>();

        if (count1 > nums.length / 3) res.add(tmp1);
        if (count2 > nums.length / 3) res.add(tmp2);

        return res;
    }
}
