package com.leetCode.array;

/**
 * @author 徐其伟
 * @Description:
 * 330 Patching Array
 *
 * @date 2019/6/8 20:12
 */
public class PatchingArray {
    public static void main(String[] args) {
//        System.out.println(new find().minPatches(new int[]{1, 3}, 6));
        System.out.println(new PatchingArray().minPatches(new int[]{1, 10, 5}, 20));
    }


    /**
     * 往nums添加元素，使得nums取任意元素能
     */
    public int minPatches(int[] nums, int n) {
        long cur_max = nums.length == 0 || nums[0] > 1 ? 0 : 1;

        int ans = 0;
        for(int i = 0; i < nums.length; i++) {
            int e = nums[i];
            if(e >= n || cur_max >= n) break;
            //判断这个数组全部用起来能不能够到n
            while(cur_max + 1 < e && cur_max < n) {
                ans++;
                cur_max += cur_max + 1;
            }
            if(i != 0 || e != 1) cur_max += e;
        }
        //还够不到，继续操作
        while(cur_max < n) {
            ans++;
            cur_max += cur_max + 1;
        }
        return ans;
    }
}
