package com.leetCode.array;

import java.util.Arrays;

/**
 * 31. 下一个排列
 * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
 *
 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
 *
 * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
 * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
 * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
 * 给你一个整数数组 nums ，找出 nums 的下一个排列。
 *
 * 必须 原地 修改，只允许使用额外常数空间。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/next-permutation
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class NextPermutation {
    public static void main(String[] args) {
        int[] nums = {0, 1, 4, 3, 2};
        new NextPermutation().nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }

    public void nextPermutation(int[] nums) {
        //0 1 4 2 3
        //0 1 4 3 2
        //0 2 1 3 4
        //从尾开始找到第一个比前一个大的 (1)
        int i = nums.length - 1;
        for(; i > 0 ; i--) {
            if (nums[i] > nums[i - 1]) {
                i--;
                break;
            }
        }
        //从尾找到第一个比1大的
        int j = nums.length - 1;
        for(; j > 0 ; j--) {
            if (nums[i] < nums[j]) {
                break;
            }
        }
        System.out.println(i + " " + j);
    
        //交换两个值
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
        
        //如果没有下一个排列就从头到尾翻转
        if (i != 0 || j != 0) {
            i++;
        }
        //反转i后的所有数值
        j = nums.length - 1;
        while(i < j) {
            tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
            i++;
            j--;
        }
    }
}