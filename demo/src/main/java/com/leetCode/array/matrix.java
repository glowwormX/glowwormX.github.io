package com.leetCode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐其伟
 * @Description:
 * @date 2019/6/9 20:47
 */
public class matrix {
    public static void main(String[] args) {
        //[[1,2,3,4],[5,6,7,8],[9,10,11,12]]
        //[[2,5],[8,4],[0,-1]]
        int [] a1 = {2,5};
        int [] a2 = {8,4};
        int [] a3 = {0,-1};
        int[][] matrix = {a1,a2,a3};
        System.out.println(new matrix().spiralOrder(matrix));
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int rows = matrix.length;
        if (rows == 0) return res;
        int cols = matrix[0].length;
        int level = 0;
        int min = Math.min(rows/2, cols/2);
        while (level < min) {
            for (int i = level;i < cols - level;i++) {
                res.add(matrix[level][i]);
            }
            for (int i = level + 1;i < rows - level;i++) {
                res.add(matrix[i][cols - level - 1]);
            }
            for (int i = cols - level - 2;i >= level;i--) {
                res.add(matrix[rows - level - 1][i]);
            }
            for (int i = rows - level - 2; i> level; i--) {
                res.add(matrix[i][level]);
            }
            level++;
        }
        if (rows == cols) {
            res.add(matrix[rows/2][cols/2]);
            return res;
        } else if (rows < cols) {
            for (int i = level;i < cols - level && level < rows - 1;i++) res.add(matrix[level][i]);
        } else {
            for (int i = level;i < rows - level && level < cols - 1;i++) res.add(matrix[i][level]);
        }
        return res;
    }

}

