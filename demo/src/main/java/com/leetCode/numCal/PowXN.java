package com.leetCode.numCal;

/**
 * @author 徐其伟
 * @Description:
 * @date 2019/6/9 19:48
 */
public class PowXN {
    public static void main(String[] args) {
        new PowXN().pow(2, 10);
    }
    public double pow(double x, int n) {
        if (n == 0)
            return 1.0;
        double res = 1.0;
        if (n < 0) {
            if (x >= 1.0 / Double.MAX_VALUE || x <= 1.0 / -Double.MAX_VALUE)
                x = 1.0 / x;
            else
                return Double.MAX_VALUE;
            if (n == Integer.MIN_VALUE) {
                res *= x;
                n++;
            }
        }
        n = Math.abs(n);
        boolean isNeg = false;
        if (n % 2 == 1 && x < 0) {
            isNeg = true;
        }
        x = Math.abs(x);
        while (n > 0) {
            if ((n & 1) == 1) {
                if (res > Double.MAX_VALUE / x)
                    return Double.MAX_VALUE;
                res *= x;
            }
            x *= x;
            n = n >> 1;
        }
        return isNeg ? -res : res;
    }
}
