package com.leetCode.binarySearch;

/**
 * @author 徐其伟
 * @Description: 二分查找旋转排序的数组
 * order 33
 * @date 2019/6/7 19:45
 */
public class SearchInRotatedSortedArray {
    public static void main(String[] args) {
        int[] nums = {4,5,6,7,8,1,2,3};
        System.out.println(new SearchInRotatedSortedArray().search(nums, 0, nums.length - 1, 8));

        int[] nums2 = {3,1,1};
        System.out.println(new SearchInRotatedSortedArray().search(nums2, 0, nums2.length - 1, 3));
        System.out.println(new SearchInRotatedSortedArray().search(nums2, 3));

    }

    public int search(int[] nums, int l, int r, int target) {
        int mid = (l + r) / 2;
        if (l > r) {
            return -1;
        }
        if (nums[mid] == target) {
            return mid;
        } else if (nums[l] < nums[mid]) {
            if (target < nums[mid] && target >= nums[l]) {
                return search(nums, l, mid - 1, target);
            } else {
                return search(nums, mid + 1, r, target);
            }
        } else if (nums[l] > nums[mid]) {
            if (target > nums[mid] && target <= nums[r] ) {
                return search(nums, mid + 1, r, target);
            } else {
                return search(nums, l, mid - 1, target);
            }
        }else {
            return search(nums, l+1, r, target);
        }
    }


    public boolean search(int[] A, int target) {
        if (A == null || A.length == 0)
            return false;
        int l = 0;
        int r = A.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (A[m] == target)
                return true;
            if (A[m] > A[l]) {
                if (A[m] > target && A[l] <= target) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            } else if (A[m] < A[l]) {
                if (A[m] < target && A[r] >= target) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            } else {
                l++;
            }
        }
        return false;
    }
}
