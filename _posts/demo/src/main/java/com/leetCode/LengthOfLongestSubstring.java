package com.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐其伟
 * @Description:
 * @date 19-6-5 下午4:35
 */
public class LengthOfLongestSubstring {
    public static void main(String[] args) {
        int res = lengthOfLongestSubstring2("abcabcbb");
        System.out.println(res);
    }

    public static int lengthOfLongestSubstring2(String s) {
        int max = 0;
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            while (j < s.length() && j == s.indexOf(s.charAt(j), i)) {
                j++;
            }
            max = Math.max(j - i, max);
        }
        return max;
    }


        public static int lengthOfLongestSubstring(String s) {
        List<Character> list;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            list = new ArrayList<Character>();
            for (int j = i; j < s.length(); j++) {
                if (list.contains(s.charAt(j))) {
                    if (res < list.size()) {
                        res = list.size();
                    }
                    break;
                } else {
                    list.add(s.charAt(j));
                }
            }
            if (res < list.size()) {
                res = list.size();
            }
        }
        return res;
    }

    public static int lengthOfLongestSubstring1(String s) {
        int max = 0;
        if (s == null || s.isEmpty()) {
            return max;
        }

        int left = 0, right = 0, n = s.length();
        while (right < n) {
            while (right < n && s.indexOf(s.charAt(right), left) == right) {
                right++;
            }

            max = Math.max(max, right - left);

            if (right < n) {
                int ind = s.indexOf(s.charAt(right), left);
                left = ind == left ? left + 1 : ind;
            }
        }

        return max;
    }
}
