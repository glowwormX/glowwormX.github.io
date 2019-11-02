package com.leetCode.numCal;

/**
 * 50  Pow(x, n)
 */
public class MyPowXn {
    public static void main(String[] args) {
        System.out.println(new MyPowXn().myPow(1.0D, 3));
    }

    public double myPow(double x, int n) {
        if (x == -1) {
            if ((n & 1) != 0) {
                return -1;
            } else {
                return 1;
            }
        }
        if (x == 1)
            return 1;

        if (n == -2147483648) {
            return 0;
        }
        double mul = 1;
        if (n > 0) {
            mul = powIteration(x, n);
        } else {
            n = -n;
            mul = powIteration(x, n);
            mul = 1 / mul;
        }
        return mul;
    }

    public double powIteration(double x, int n) {
        double ans = 1;
        //遍历每一位
        while (n > 0) {
            //最后一位是 1，加到累乘结果里
            if ((n & 1) == 1) {
                ans = ans * x;
            }
            //更新 x
            x = x * x;
            //n 右移一位
            n = n >> 1;
        }
        return ans;
    }
}
