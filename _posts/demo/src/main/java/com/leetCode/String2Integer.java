package com.leetCode;

/**
 * @author 徐其伟
 * @Description:
 * order 8
 * @date 2019/6/7 19:30
 */
public class String2Integer {
    public static void main(String[] args) {
        System.out.println(new String2Integer().myAtoi("   -34545"));
    }

    public int myAtoi(String str) {
        int len = str.length();
        boolean flag = false;
        int i = 0;
        for(; i < len && str.charAt(i) == ' '; i++);
        if (i < len && str.charAt(i) == '+') i++;
        else if (i < len && str.charAt(i) == '-') {i++;flag = true;}

        int max_10 = Integer.MAX_VALUE / 10;
        int res = 0;
        while (i < len) {
            int temp = str.charAt(i) - '0';
            if (temp < 0 || temp > 10) break;
            if (res > max_10 && !flag) return Integer.MAX_VALUE;
            if (res > max_10 && flag) return Integer.MIN_VALUE;
            if (res == max_10 && temp > 7 && !flag) return Integer.MAX_VALUE;
            if (res == max_10 && temp > 8 && flag) return Integer.MIN_VALUE;
            res = res * 10 + temp;
            i++;
        }
        if (flag) res = res * -1;
        return res;
    }
}
