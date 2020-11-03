package com.leetCode;

import java.io.UnsupportedEncodingException;

/**
 * @author 徐其伟
 * @Description: 快排
 * order 215 Kth Largest Element in an Array
 * @date 2019/5/30 21:48
 */
public class QuickSort {
    // 3 0 7 1 2 5
    // 2 0 7 1 7 5
    void quickSort1(int[] arr, int l, int r) {
        int compare = arr[l];
        int i = l, j = r;
        while (i < j) {
            while (i < j && arr[i] < compare) {
                i++;
            }
            while (i > j && arr[j] > compare) {
                j--;
            }
            int temp = arr[i];
            arr[l] = arr[j];
            arr[i] = compare;
            arr[j] = temp;
        }
        quickSort1(arr, l, i - 1);
        quickSort1(arr, i + 1, r);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        int[] arr = {4,6,2,77,23,9,3,7,31,15,13,17};
//               i             j                     4
//        arr = {3,6,2,77,23,9,3,7,31,15,13,17};
//                 i           j                     4
//        arr = {3,6,2,77,23,9,6,7,31,15,13,17};
//                   jj                               4
//        arr = {3,2,2,77,23,9,6,7,31,15,13,17};
//                 i j                               4
//        arr = {3,2,4,77,23,9,6,7,31,15,13,17};
        quickSort(arr, 0, arr.length - 1);
//        arr = new int[]{3,2,1,5,6,4};
        int i = KMax(arr, 0, arr.length - 1, 2);
        System.out.println(i);
Re
    }

//    private int sort(int[] arr) {
////        quickSort();
//    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left > right) return;
        int temp = arr[left]; //取第一个为基数，拿来对比，并缓存在temp里
        int i = left, j = right;
        while (i < j) {
            //找出右边比基数小的，返回的j为下标
            while (i < j && temp < arr[j]) {
                j--;
            }
            //先赋值再i++，将小的数给i下表的
            if (i < j) {
                arr[i++] = arr[j];
            }
            //找出左边边比基数大的，返回的i为下标
            while (i < j && temp > arr[i]) {
                i++;
            }
            //将大的数给j下表的
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        //把基数给最后中间的位置
        arr[i] = temp;
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }


    /** 快排变种
     * @param nums
     * @param l
     * @param r
     * @param k
     * @return
     */
    public static int KMax(int[] nums, int l, int r, int k) {
        int i = l, j = r;
        int temp = nums[i];
        while (i < j) {
            while (i < j && temp < nums[j]) {
                j--;
            }
            if (i < j) {
                nums[i++] = nums[j];
            }
            while (i < j && temp > nums[i]) {
                i++;
            }
            if (i < j) {
                nums[j--] = nums[i];
            }
        }
        nums[i] = temp;
        if (k == nums.length - i) { //从小到大排，第i个为 nums.length - i大
            return nums[i];
        } else if (k < nums.length - i) {
            return KMax(nums, i + 1, r, k);
        } else {
            return KMax(nums, 0, i - 1, k);
        }
    }
}
