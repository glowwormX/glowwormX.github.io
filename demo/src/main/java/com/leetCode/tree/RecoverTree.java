package com.leetCode.tree;

/**
 * 99. 恢复二叉搜索树
 * 给你二叉搜索树的根节点 root ，该树中的 恰好 两个节点的值被错误地交换。请在不改变其结构的情况下，恢复这棵树
 */
public class RecoverTree {
    // 1 4 3 2 5
    //   4>3>2
    //1 2 3 5 4
    public static void main(String[] args) {
        int[] preorder = {3, 4, 1, 2, 5};
        int[] inorder = {1, 4, 3, 2, 5};
        TreeNode treeNode = new PreInOrderBuildTree().buildTree(preorder, inorder);
        treeNode.printAll();

        new RecoverTree().recoverTree(treeNode);

        treeNode.printAll();
    }

    public void recoverTree(TreeNode root) {
        dps(root);
        if (x != null && y != null) {
            int tmp = x.val;
            x.val = y.val;
            y.val = tmp;
        }
    }

    TreeNode p, x, y;

    public void dps(TreeNode c) {
        if (c == null) return;
        System.out.println("dfs," + c.val);
        dps(c.left);
        if (p != null && p.val > c.val) {
            if (x == null) {
                x = p;
                y = c;
                System.out.println(x.val + "," + y.val);
            } else {
                y = c;
                System.out.println(y.val);
            }
        }
        p = c;
        dps(c.right);
    }
}
