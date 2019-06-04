package com.leetCode;

/**
 * @author 徐其伟
 * @Description: 求一个数组中连续子向量的最大和
 * @date 19-6-4 下午2:45
 */
public class FindGreatestSumOfSubArray {

//    F(i)：以array[i]为末尾元素的子数组的和的最大值，子数组的元素的相对位置不变
//
//    F(i)=max{F(i-1)+array[i], array[i]}
//
//    res：所有子数组的和的最大值
//
//    res=max(res，F(i))
    
    public static int findGreatestSumOfSubArray(int[] array) {
        int res = array[0]; //记录当前所有子数组的和的最大值
        int max=array[0];   //包含array[i]的连续数组最大值
        for (int i = 1; i < array.length; i++) {
            max=Math.max(max+array[i], array[i]);
            res=Math.max(max, res);
        }
        return res;
    }

    public static void main(String[] args) {
        int res = findGreatestSumOfSubArray(new int[]{-3, 6, -2, 7, -15, 1, 2, 2});
        System.out.println(res);
    }
}
