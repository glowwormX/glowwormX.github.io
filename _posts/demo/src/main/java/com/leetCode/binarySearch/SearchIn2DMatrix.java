package com.leetCode.binarySearch;

/**
 * @author 徐其伟
 * @Description: 有序矩阵二分查找
 * order 74
 * @date 2019/6/7 22:40
 */
public class SearchIn2DMatrix {
    public static void main(String[] args) {
//        [[1,3,5,7],[10,11,16,20],[23,30,34,50]]
//        int [] a1 = {1,3,5,7};
//        int [] a2 = {10,11,16,20};
//        int [] a3 = {23,30,34,50};
//        int[][] matrix = {a1,a2,a3};
        int [] a1 = {1};
        int [] a2 = {3};
        int[][] matrix = {a1,a2};
        System.out.println(new SearchIn2DMatrix().searchMatrix(matrix, 2));
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int m = matrix.length - 1, n = matrix[0].length - 1;
        int ml = 0, mr = m, nl = 0, nr = n;
        while (ml < mr) {
            int midm = (mr + ml)/2;
            if (matrix[midm][0] == target || target == matrix[midm][matrix[midm].length - 1]) {
                return true;
            } else if (matrix[midm][0] < target && target < matrix[midm][matrix[midm].length - 1]) {
                ml = midm;
                break;
            } else if (matrix[midm][0] < target) {
                ml = midm + 1;
            } else {
                mr = midm - 1;
            }
        }
        while (nl <= nr) {
            int midn = (nr + nl)/2;
            if (matrix[ml][midn] == target) {
                return true;
            } else if (matrix[ml][midn] < target) {
                nl = midn + 1;
            } else {
                nr = midn - 1;
            }
        }
        return false;
    }
}
