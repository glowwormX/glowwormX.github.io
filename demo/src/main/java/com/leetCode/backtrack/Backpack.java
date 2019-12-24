package com.leetCode.backtrack;
import java.util.*;

/**
 * @author 徐其伟
 * @Description: 背包问题
 * @date 2019/11/8 20:34
 */
public class Backpack {
        public static void main(String[] args) {
            int[] a = new int[5];
            /* 输入：第一行：1000 钱，5个商品
            价格 权重 父级(>1为附件，父级必须要买)
            1200 5
            800 2 0
            400 5 1
            300 5 1
            400 3 0
            500 2 0
            输出 2200
            */
            Scanner input = new Scanner(System.in);
            while (input.hasNextInt()) {
                int n = input.nextInt(); // 总钱数
                int m = input.nextInt(); // 商品个数
                int[] p = new int[m];
                int[] v = new int[m];
                int[] q = new int[m];
                for (int i = 0; i < m; i++) {
                    p[i] = input.nextInt();        // 价格
                    v[i] = input.nextInt() * p[i]; // 价值
                    q[i] = input.nextInt();        // 主or附件
                }
                int[] sumsLast = new int[n/10 + 1];
                int[] sumsRes = new int[n/10 + 1];
                for (int i = 1; i <= m; i++) {
                    int [] temp = sumsLast;
                    sumsLast = sumsRes;
                    sumsRes = temp;
                    for (int j = 1; j <= n/10; j++) {
                        if (q[i-1] == 0) {
                            if (j < p[i-1]/10) {
                                sumsRes[j] = sumsLast[j];
                            } else {
                                sumsRes[j] = Math.max(sumsLast[j], v[i-1] + sumsLast[j - p[i-1]/10]);
                            }
                        } else {
                            if (j < (p[q[i-1]-1] + p[i-1]) / 10) {
                                sumsRes[j] = sumsLast[j];
                            } else {
                                sumsRes[j] = Math.max(sumsLast[j], v[i-1] + v[q[i-1]-1] + sumsLast[j - p[i-1]/10 -p[q[i-1]-1]/10]);
                            }
                        }
                        System.out.print("i:"+ i + "  j: " + j + "  sum:" + sumsRes[j]);
                    }
                    System.out.println();
                }
                System.out.println(sumsRes[n/10]);

//                int[][] sums = new int[m + 1][n/10 + 1];
//                for (int i = 1; i <= m; i++) {
//                    for (int j = 1; j <= n/10; j++) {
//                        if (q[i-1] == 0) {
//                            if (j < p[i-1]/10) {
//                                sums[i][j] = sums[i-1][j];
//                            } else {
//                                sums[i][j] = Math.max(sums[i-1][j], v[i-1] + sums[i-1][j - p[i-1]/10]);
//                            }
//                        } else {
//                            if (j < (p[q[i-1]-1] + p[i-1]) / 10) {
//                                sums[i][j] = sums[i-1][j];
//                            } else {
//                                sums[i][j] = Math.max(sums[i-1][j], v[i-1] + v[q[i-1]-1] + sums[i-1][j - p[i-1]/10 -p[q[i-1]-1]/10]);
//                            }
//                        }
//                        System.out.print("i:"+ i + "  j: " + j + "  sum:" + sums[i][j]);
//                    }
//                    System.out.println();
//                }
//                System.out.println(sums[m][n/10]);
            }
        }
}
