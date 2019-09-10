package com.leetCode.tree;

/**
 * @author 徐其伟
 * @Description:
 * @date 19-8-23 上午8:59
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        String s = val + "";
        if (left != null) s += " left: " + left.val;
        if (right != null) s += " right: " + right.val;
        return s;
    }
}
