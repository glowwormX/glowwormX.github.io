package com.leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        streamSortTest();
//        int[][] a = {{0, 0, 1, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 1}, {0, 0, 1, 0, 1, 0, 0}, {0, 0, 0, 1, 1, 1, 0}, {1, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 0, 1}, {0, 0, 1, 0, 0, 0, 0}};
//        new Solution7().shortestPathBinaryMatrix(a);
//        System.out.println(new Solution6().numSubarrayProductLessThanK(new int[]{10,5,2,6}, 100));
//        new Solution3().validPalindrome("abc");
//        new Solution1().combinationSum(new int[]{2,3,6,7}, 7);
//        System.out.println(new Solution().minInsertions("(()))"));
    }

    public static void streamSortTest() {
        // 排序大小
        final int sortSize = 1000000;

        // 通过stream和一个Integer数组创建一个list
        List<Integer> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < sortSize; i++) {
            list.add(r.nextInt());
        }
        System.out.println("Arrays created!");

        long startTime = System.currentTimeMillis();
        int[] res = list.stream().parallel().mapToInt(Integer::intValue).sorted().toArray();
        long endTime = System.currentTimeMillis();
        System.out.println("ParallelStream time is: " + (endTime - startTime) + " milliseconds");
    }

    static class Solution7 {
        int len = 0;

        public int shortestPathBinaryMatrix(int[][] grid) {
            //MINij = MIN(8方向) + 1
            if (grid[0][0] == 1) return -1;
            len = grid.length;
            Integer[][] dp = new Integer[len][len];
            dp[0][0] = 1;
            minAll(grid, dp, 0, 0, 0);


            return dp[len - 1][len - 1] == null ? -1 : dp[len - 1][len - 1];
        }

        int[][] direct = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, 1}, {0, -1}, {-1, -1}};

        public int min(Integer a, Integer b) {
            if (a == null) return b;
            if (b == null) return a;
            return a > b ? b : a;
        }

        public void minAll(int[][] grid, Integer[][] dp, int i, int j, int path) {
            if (dp[i][j] != null && path > dp[i][j]) return;
            for (int k = 0; k < direct.length; k++) {
                int x = i + direct[k][0];
                int y = j + direct[k][1];

                if (x >= 0 && y >= 0 && x < len && y < len && grid[x][y] == 0) {
                    dp[i][j] = min(path + 1, dp[i][j]);
                    if (i == 2 && j == 6) System.out.println(i + "," + j + " : " + dp[i][j]);
                    // System.out.println(x + "," + y);
                    // System.out.println("res: " + dp[i][j]);
                    minAll(grid, dp, x, y, path + 1);
                }
            }
        }
    }
//0 0 1 0 0 0 0
//0 1 0 0 0 0 1
//0 0 1 0 1 0 0
//0 0 0 1 1 1 0
//1 0 0 1 1 0 0
//1 1 1 1 1 0 1
//0 0 1 0 0 0 0


    // 0 1 1 0 0 0
// 0 1 0 1 1 0
// 0 1 1 0 1 0
// 0 0 0 1 1 0
// 1 1 1 1 1 0
// 1 1 1 1 1 0

    static class Solution6 {
        public int numSubarrayProductLessThanK(int[] nums, int k) {
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                int cache = 1;
                for (int j = i; j < nums.length; j++) {
                    cache = cache * nums[j];
                    if (cache < k) {
                        res++;
                    } else {
                        break;
                    }
                }
            }
            return res;
        }
    }

    class Solution5 {
        public double soupServings(int N) {
            N = N / 25 + (N % 25 == 0 ? 0 : 1);
            if (N >= 200) return 1;
            double[][] dp = new double[N + 1][N + 1];
            // 初始化
            dp[0][0] = 0.5;
            for (int j = 1; j <= N; ++j) dp[0][j] = 1;

            for (int i = 1; i <= N; ++i) {
                for (int j = 1; j <= N; ++j) {
                    dp[i][j] += dp[Math.max(i - 4, 0)][j];
                    dp[i][j] += dp[Math.max(i - 3, 0)][j - 1];
                    dp[i][j] += dp[Math.max(i - 2, 0)][Math.max(j - 2, 0)];
                    dp[i][j] += dp[i - 1][Math.max(j - 3, 0)];
                    dp[i][j] /= 4;
                }
            }
            return dp[N][N];
        }
    }

    static class Solution4 {
        public double soupServings(int N) {
            N = N / 25 + (N % 25 > 0 ? 1 : 0);
            if (N >= 500) return 1.0;

            double[][] memo = new double[N + 1][N + 1];
            for (int s = 0; s <= N; ++s) {
                for (int i = 0; i <= N; ++i) {
                    int j = s - i;
                    if (j < 0 || j > N) continue;
                    double ans = 0.0;
                    if (i == 0) ans = 1.0;
                    if (i == 0 && j == 0) ans = 0.5;
                    if (i > 0 && j > 0) {
                        ans = 0.25 * (memo[M(i - 4)][j] + memo[M(i - 3)][M(j - 1)] +
                                memo[M(i - 2)][M(j - 2)] + memo[M(i - 1)][M(j - 3)]);
                    }
                    memo[i][j] = ans;

                }
            }
            return memo[N][N];
        }

        public int M(int x) {
            return Math.max(0, x);
        }
    }

    static class Solution {
        public int minInsertions(String s) {
            int num = 0;
            for (int i = 0; i < s.length(); i++) {
                if ('(' == s.charAt(i)) {
                    num = num + 2;
                } else if (')' == s.charAt(i)) {
                    if (num - 1 < 0) {
                        num = num + 3;
                        num += 2;
                    } else {
                        num = num - 1;
                    }

                }
            }
            return num;
        }
    }

    static class Solution1 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> res = new ArrayList<>();
            combinationSum(candidates, target, new ArrayList<>(), 0, res);
            return res;
        }

        public void combinationSum(int[] candidates, int target, List<Integer> cache, int start, List<List<Integer>> res) {
            if (target == 0) {
                res.add(new ArrayList<>(cache));
                return;
            } else if (target < candidates[start]) {
                return;
            }
            for (int i = start; i < candidates.length; i++) {
                int current = candidates[i];
                cache.add(current);
                combinationSum(candidates, target - current, cache, start, res);
                cache.remove(cache.size() - 1);
                if (cache.size() == 0) {
                    start++;
                }
            }
        }
    }

    static class Solution3 {
        public boolean validPalindrome(String s) {
            for (int i = -1; i < s.length() - 1; i++) {
                if (validPalindrome(s, i)) {
                    return true;
                }
            }
            return false;
        }

        public boolean validPalindrome(String s, int n) {
            for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
                if (n == i) {
                    i++;
                    continue;
                } else if (n == j) {
                    j--;
                    continue;
                }
                if (s.charAt(i) != s.charAt(j)) {
                    return false;
                }
            }
            return true;
        }
    }

}
