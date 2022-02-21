package com.leetCode.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void printAll() {
        printAll(Collections.singletonList(this));
    }

    private void printAll(List<TreeNode> treeNodes) {
        List<TreeNode> list = new ArrayList<>();
        for (TreeNode node : treeNodes) {
            if (node != null) {
                System.out.print(node.val + " ");
                list.add(node.left);
                list.add(node.right);
            } else {
                System.out.print("null ");
            }
        }
        System.out.println();
        if (!list.isEmpty()) printAll(list);
    }
}
