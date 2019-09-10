package com.leetCode.tree;


/**
 * @author 徐其伟
 * @Description: 根据先序 中序构建树
 * 105. Construct Binary Tree from Preorder and Inorder Traversal
 * @date 19-8-21 下午2:42
 */
public class PreInOrderBuildTree {
    public static void main(String[] args) {
        int[] preorder = {3,9,20,15,7};
        int[] inorder = {9,3,15,20,7};
//        int[] preorder = {1,2,3};
//        int[] inorder = {2,3,1};
        TreeNode treeNode = new PreInOrderBuildTree().buildTree(preorder, inorder);
    }

//     Definition for a binary tree node.

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        return buildTree(preorder, inorder, 0, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int[] inorder, int preorderLeft, int inorderLeft, int inorderRight ) {
        TreeNode first = new TreeNode(preorder[preorderLeft]);
        int inorderMid = indexOf(inorder, preorder[preorderLeft]);
//        if (!(inorderMid > inorderLeft && inorderMid < inorderRight)) throw new IllegalArgumentException("can't build");
        if (inorderMid > inorderLeft) {
            first.left = buildTree(preorder, inorder, preorderLeft + 1,  inorderLeft, inorderMid - 1);
        }
        if (inorderMid < inorderRight) {
            first.right = buildTree(preorder, inorder, preorderLeft + inorderMid - inorderLeft + 1,  inorderMid + 1, inorderRight);
        }
        return first;
    }

    private int indexOf(int [] array, int val) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == val) return i;
        }
        return -1;
    }

//    public TreeNode buildTree(int[] preorder, int[] inorder) {
//        if (preorder.length == 0) return null;
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = 0; i < inorder.length; i++) {
//            map.put(inorder[i], i);
//        }
//        return buildTree(preorder, map, 0, preorder.length - 1, 0, inorder.length - 1);
//    }
//
//    private TreeNode buildTree(int[] pre, Map<Integer, Integer> map, int pl, int pr, int il, int ir) {
//        if (pl > pr) return null;
//        if (pl == pr) return new TreeNode(pre[pl]);
//        int mi = map.get(pre[pl]);
//        TreeNode node = new TreeNode(pre[pl]);
//        node.left = buildTree(pre, map, pl + 1, pl + mi - il, il, mi - 1);
//        node.right = buildTree(pre, map, pl + mi - il + 1, pr, mi + 1, ir);
//        return node;
//    }
}
