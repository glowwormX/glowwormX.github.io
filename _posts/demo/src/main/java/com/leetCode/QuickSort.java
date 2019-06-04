package com.leetCode;

import java.io.UnsupportedEncodingException;

/**
 * @author 徐其伟
 * @Description: 快排
 * @date 2019/5/30 21:48
 */
public class QuickSort {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-0.125F)));
//        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-5F)));
//        System.out.println(Integer.toBinaryString(Float.floatToIntBits(5F)));
//        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-3.125F)));
//
//        char a = '好';
//        String str = "g";
//        byte[] bytes = str.getBytes();
//        int byte_len = bytes.length;
//        System.out.println(bytes + "字节长度：" + byte_len);

        int[] arr = {4,6,2,77,23,9,3,7,31,15,13,17};
        quickSort(arr, 0, arr.length - 1);

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

    private static void swap(int[] arr, int i, int j) {
        int x = arr[i];
        arr[i] = arr[j];
        arr[j] = x;
    }
}
