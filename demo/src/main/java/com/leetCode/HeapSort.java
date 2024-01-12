package com.leetCode;

import java.util.Arrays;

/**
 * @Description:<p>堆排序算法的实现，以大顶堆为例。</p>
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8};
        heapSort(ints);
    }

    /**
     * 堆筛选，除了start之外，start~end均满足大顶堆的定义。
     * 调整之后start~end称为一个大顶堆。
     *
     * @param arr   待调整数组
     * @param start 起始指针
     * @param end   结束指针
     */
    public static void heapAdjust(int[] arr, int start, int end) {
        int temp = arr[start];

        for (int i = 2 * start + 1; i <= end; i = 2 * i + 1) {
            //左右孩子的节点分别为2*i+1,2*i+2

            //选择出左右孩子较大的下标
            if (i < end && arr[i] < arr[i + 1]) {
                i++;
            }
            if (temp >= arr[i]) {
                break; //已经为大顶堆，=保持稳定性。
            }
            arr[start] = arr[i]; //将子节点上移
            start = i; //下一轮筛选
        }

        arr[start] = temp; //插入正确的位置
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        System.out.println("input:" + Arrays.toString(arr));

        //建立大顶堆
        for (int i = arr.length / 2; i >= 0; i--) {
            heapAdjust(arr, i, arr.length - 1);
            System.out.println("i:" + i + ",arr:" + Arrays.toString(arr));
        }
        System.out.println("build end");
        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapAdjust(arr, 0, i - 1);
            System.out.println(Arrays.toString(arr));
        }

    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
