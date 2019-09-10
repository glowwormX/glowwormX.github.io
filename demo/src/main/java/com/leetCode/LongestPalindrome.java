package com.leetCode;

/**
 * @author 徐其伟
 * @Description: 最大回文路径
 * order：3
 * @date 19-6-5 下午7:22
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        String babad = new LongestPalindrome().longestPalindrome1("babad");
        System.out.println(babad);
    }

    public String longestPalindrome(String s) {
        int max = -1;
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = s.length() - 1; j >= i && max < (j - i); j--) {
                if (isResult(s, i, j)) {
                    if (max < j - i) {
                        max = j - i;
                        res = s.substring(i, j + 1);
                    }
                    break;
                }
            }
        }
        return res;
    }

    public String longestPalindrome1(String s) {
        int max = -1, i = 0;
        String res = "";
        while (i < s.length()) {
            int length = 1;
            int l = i, r = i;
            while (r + 1 < s.length() && s.charAt(i) == s.charAt(r + 1)) {
                r++;
            }
            while (l - 1 > 0 && s.charAt(i) == s.charAt(l - 1)) {
                l--;
            }

            while (r + length <= (s.length() - 1) && l - length >= 0 && s.charAt(r + length) == s.charAt(l - length)) {
                length++;
            }
            String temp = s.substring(l - length + 1, r + length - 1 + 1);

            if (max < temp.length()) {
                max = temp.length();
                res = temp;
            }
            i = r + 1;
        }
        return res;
    }


    private boolean isResult(String s, int l, int r) {
        while (l < r && s.charAt(l) == s.charAt(r)) {
            l++;
            r--;
        }
        return l == r || (l - r) == 1;
    }

    private String getMax(String s, int i) {
        int length = 1;
        int l = i, r = i;
        while (r + 1 < s.length() && s.charAt(i) == s.charAt(r + 1)) {
            r++;
        }
        while (l - 1 > 0 && s.charAt(i) == s.charAt(l - 1)) {
            l--;
        }

        while (r + length <= (s.length() - 1) && l - length >= 0 && s.charAt(r + length) == s.charAt(l - length)) {
            length++;
        }
        return s.substring(l - length + 1, r + length - 1 + 1);
    }
}
