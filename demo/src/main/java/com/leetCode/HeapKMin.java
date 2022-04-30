package com.leetCode;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @Description:<p>堆排序算法的实现，以大顶堆为例。</p>
 */
public class HeapKMin {
    public static void main(String[] args) {
        String[] strings = {"ab", "a", "ac", "b1"};
        Arrays.sort(strings);
        System.out.println(Arrays.toString(strings));

        int[] arr1 = {-61087, -60981, -23620, -56074, -48496, -8737, -41, -37092, -26607, -10603, -16699, 37591, 3399, -2482, 2638, -6, 0, 402, 35778, -7410, 7, 64, -33152, -24528, -4818, 531, 2031, 96, -911, 52500, 664, 861, 9396, -223, 37663, 683, -390, 118, 539, 1458, -4977, 31, 1, -3834, -48874, 53, 397, 7, 5541, -76, 601, 30, -1712, 202, -4, -9417, 31, 365, 3, 39546, 61, 50068, 84, -23702, -1, 775, 5134, 8, 60947, 40202, -1919, -4283, -3, -22721, -6, -23957, -726, -524, 51, -8, 790, -9972, 80, 0, -8, -714, -446, 0, 4, -7197, -355, -140, 6, 9, -21019, 9, -15357, -30, -565, 4, -4, 6, 3, 8762, 7, 42754, -16, -211, 140, 53936, -8, -6263, 3, -715, 4, -67, 5747, 5363, -22, 4, -220, -59, 5, -868, 52, 4, 38, 43349, 41752, 8, 29};
        int[] arr2 = {-61087, -60981, -56074, -48874, -48496, -37092, -33152, -26607, -24528, -23957, -23702, -23620, -22721, -21019, -16699, -15357, -10603, -9972, -9417, -8737, -7410, -7197, -6263, -4977, -4818, -4283, -3834, -2482, -1919, -1712, -911, -868, -726, -715, -714, -565, -524, -446, -390, -355, -223, -220, -211, -140, -76, -67, -59, -41, -30, -22, -16, -8, -8, -8, -6, -6, -4, -4, -3, -1, 0, 0, 0, 1, 3, 3, 3, 4, 4, 4, 4, 4, 5, 6, 6, 7, 7, 7, 8, 8, 9, 9, 29, 30, 31, 31, 38, 51, 52, 53, 61, 64, 80, 84, 96, 118, 140, 202, 365, 397, 402, 412, 531, 539, 601, 664, 683, 775, 790, 861, 1458, 2031, 2638, 3399, 5134, 5363, 5541, 5747, 8681, 8762, 9396, 35778, 37591, 37663, 39546, 40202, 41752, 42754, 43349, 50068, 52500};
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        for (int i = 0; i < arr1.length; i++) {
            assert arr1[i] != arr2[i];
        }

//        1 2 3 4 5 6 7 8
        int[] arrays = {8, 5, 6, 7, 4, 1, 2, 3};
        KMin1 kmin1 = new KMin1(arrays, 6);
        System.out.println(kmin1.addAndGetK(9));
        System.out.println(kmin1.addAndGetK(10));
        System.out.println(kmin1.addAndGetK(3));
        System.out.println(kmin1.addAndGetK(11));

        KMin2 kmin2 = new KMin2(arrays, 6);
        kmin2.queue.stream().mapToInt(x->x).toArray();
        System.out.println(kmin2.addAndGetK(9));
        System.out.println(kmin2.addAndGetK(10));
        System.out.println(kmin2.addAndGetK(3));
        System.out.println(kmin2.addAndGetK(11));
    }

    private static class KMax {
        int[] arr;

        public KMax(int[] arrays, int k) {
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
            for (int i = 2 * start + 1; i <= end; i = 2 * i + 1) {
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

    private static class KMin1 {
        int[] arr;

        public KMin1(int[] arrays, int k) {
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
            for (int i = 2 * start + 1; i <= end; i = 2 * i + 1) {
                //大顶堆
                if (i < end && array[i] < array[i + 1]) {
                    i++;
                }
                if (tmp >= array[i]) {
                    break;
                }
                array[start] = array[i];
                start = i;
            }
            array[start] = tmp;
        }

        public int addAndGetK(int value) {
            if (value < arr[0]) {
                arr[0] = value;
                heapAdjust(arr, 0, arr.length - 1);
            }
            return arr[0];
        }
    }


    private static class KMin2 {
        PriorityQueue<Integer> queue;

        public KMin2(int[] arrays, int k) {
            queue = new PriorityQueue<>(k, (a, b) -> a.compareTo(b) * -1);
            for (int num : arrays) {
                if (queue.size() < k) {
                    queue.offer(num);
                } else {
                    addAndGetK(num);
                }
            }

        }

        public void heapAdjust(int[] array, int start, int end) {
            int tmp = array[start];
            for (int i = 2 * start + 1; i <= end; i = 2 * i + 1) {
                //大顶堆
                if (i < end && array[i] < array[i + 1]) {
                    i++;
                }
                if (tmp >= array[i]) {
                    break;
                }
                array[start] = array[i];
                start = i;
            }
            array[start] = tmp;
        }

        public int addAndGetK(int value) {
            if (queue.peek() > value) {
                queue.poll();
                queue.offer(value);
            }
            return queue.peek();
        }
    }

    /**
     * 大顶堆调整
     */
    public void headAdjust(int[] arr, int parent) {
        for (int left = parent * 2 + 1; left < arr.length; ) {
            int max = left;
            int right = left + 1;
            if (right < arr.length && arr[left] < arr[right]) {
                max = right;
            }
            if (arr[parent] >= arr[max]) {
                break;
            }
            swap(arr, parent, max);
            parent = max;
            left = parent * 2 + 1;
        }
    }

    private void swap(int[] arr, int parent, int max) {

    }
}
