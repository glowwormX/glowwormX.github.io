package com.leetCode.numCal;

/**
 * @author 徐其伟
 * @Description:
 * 7. Reverse Integer
 * @date 2019/6/7 16:26
 */
public class ReverseInt {
    public static void main(String[] args) {
        System.out.println(new ReverseInt().reverse(-123));
    }

    public int reverse(int x) {
        int rev = 0;
        while(x != 0){
            int pop = x % 10;
            x = x / 10;
            if(rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && x > Integer.MIN_VALUE % 10) ){
                rev = 0;
                break;
            }else if(rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && x < Integer.MIN_VALUE % 10)){
                rev = 0;
                break;
            }
            rev = rev * 10 + pop;
        }
        return rev;
    }
}
