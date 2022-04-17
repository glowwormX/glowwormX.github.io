package com.leetCode;

import java.util.Arrays;

/**
 * @Description:<p>堆排序算法的实现，以大顶堆为例。</p>
 */
public class HeapKMax {
    public static void main(String[] args) {
        int[] arr1 = {-61087, -60981, -23620, -56074, -48496, -8737, -41, -37092, -26607, -10603, -16699, 37591, 3399, -2482, 2638, -6, 0, 402, 35778, -7410, 7, 64, -33152, -24528, -4818, 531, 2031, 96, -911, 52500, 664, 861, 9396, -223, 37663, 683, -390, 118, 539, 1458, -4977, 31, 1, -3834, -48874, 53, 397, 7, 5541, -76, 601, 30, -1712, 202, -4, -9417, 31, 365, 3, 39546, 61, 50068, 84, -23702, -1, 775, 5134, 8, 60947, 40202, -1919, -4283, -3, -22721, -6, -23957, -726, -524, 51, -8, 790, -9972, 80, 0, -8, -714, -446, 0, 4, -7197, -355, -140, 6, 9, -21019, 9, -15357, -30, -565, 4, -4, 6, 3, 8762, 7, 42754, -16, -211, 140, 53936, -8, -6263, 3, -715, 4, -67, 5747, 5363, -22, 4, -220, -59, 5, -868, 52, 4, 38, 43349, 41752, 8, 29};
        int[] arr2 = {-61087, -60981, -56074, -48874, -48496, -37092, -33152, -26607, -24528, -23957, -23702, -23620, -22721, -21019, -16699, -15357, -10603, -9972, -9417, -8737, -7410, -7197, -6263, -4977, -4818, -4283, -3834, -2482, -1919, -1712, -911, -868, -726, -715, -714, -565, -524, -446, -390, -355, -223, -220, -211, -140, -76, -67, -59, -41, -30, -22, -16, -8, -8, -8, -6, -6, -4, -4, -3, -1, 0, 0, 0, 1, 3, 3, 3, 4, 4, 4, 4, 4, 5, 6, 6, 7, 7, 7, 8, 8, 9, 9, 29, 30, 31, 31, 38, 51, 52, 53, 61, 64, 80, 84, 96, 118, 140, 202, 365, 397, 402, 412, 531, 539, 601, 664, 683, 775, 790, 861, 1458, 2031, 2638, 3399, 5134, 5363, 5541, 5747, 8681, 8762, 9396, 35778, 37591, 37663, 39546, 40202, 41752, 42754, 43349, 50068, 52500};
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        for (int i = 0; i < arr1.length; i++) {
            assert arr1[i] == arr2[i];
        }

//        1 2 3 4 5 6 7 8
        int[] arrays = {8, 5, 6, 7, 4, 1, 2, 3};
        KMax kmax = new KMax(arrays, 6);
        System.out.println(kmax.addAndGetK(9));
        System.out.println(kmax.addAndGetK(10));
        System.out.println(kmax.addAndGetK(3));
        System.out.println(kmax.addAndGetK(11));
    }

    private static class KMax {
        int[] arr;

        public KMax(int[] arrays, int k) {
//            arr = new int[k];
//            for (int i = 0; i < k; i++) {
//                arr[i] = arrays[i];
//            }
            arr = Arrays.copyOfRange(arrays, 0, k);
            for (int i = this.arr.length / 2; i >= 0; i--) {
                heapAdjust(this.arr, i, this.arr.length - 1);
            }
            System.out.println(Arrays.toString(this.arr));

            for (int i = k; i < arrays.length; i++) {
                addAndGetK(arrays[i]);
            }
        }

        public void heapAdjust(int[] array, int start, int end) {
            int tmp = array[start];
            for (int i = start * 2 + 1; i <= end; i *= 2) {
                //小顶堆
                if (i < end && array[i] > array[i + 1]) {
                    i++;
                }
                if (tmp <= array[i]) {
                    break;
                }
                array[start] = array[i];
                start = i;
            }
            array[start] = tmp;
        }

        public int addAndGetK(int value) {
            if (value > arr[0]) {
                arr[0] = value;
                heapAdjust(arr, 0, arr.length - 1);
            }
            return arr[0];
        }
    }
}
