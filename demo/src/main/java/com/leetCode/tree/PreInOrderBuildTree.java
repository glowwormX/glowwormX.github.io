package com.leetCode.tree;


import java.util.Deque;
import java.util.LinkedList;

/**
 * @author 徐其伟
 * @Description: 根据先序 中序构建树
 * 105. Construct Binary Tree from Preorder and Inorder Traversal
 * @date 19-8-21 下午2:42
 */
public class PreInOrderBuildTree {
    public static void main(String[] args) {
        int[] preorder = {1, 2, 4, 5, 3, 6, 7};
        int[] inorder = {4, 2, 5, 1, 6, 3, 7};
//        int[] preorder = {1,2,3};
//        int[] inorder = {2,3,1};
        TreeNode treeNode = new PreInOrderBuildTree().buildTree(preorder, inorder);

        treeNode.printAll();

        System.out.print("preOrderByDeque: ");
        printPreOrderByDeque(treeNode);
        System.out.println("");

        System.out.print("inOrder: ");
        printInOrder(treeNode);
        System.out.println("");

        System.out.print("inOrderByDeque: ");
        printInOrderByDeque(treeNode);
        System.out.println("");

        System.out.print("bfs: ");
        printBfsByDeque(treeNode);
        System.out.println("");
    }

//     Definition for a binary tree node.

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        return buildTree(preorder, inorder, 0, 0, inorder.length - 1);
    }

    /**
     * {1, 2, 4, 5, 3, 6, 7};
     * {4, 2, 5, 1, 6, 3, 7};
     * 构建后：
     *    1
     *   2 3
     * 4 5 6 7
     * 先拿”1“去找中序中的位置inorderMid，
     * 将中序分成左右两段，inorderMid左边的都是”1“的左节点，右边的都是右节点的数据
     * 将先序分成前后两段，中序inorderMid左边有三个数，所以2是左节点，3是右节点, (preorderLeft + 1), (preorderLeft + 1 + inorderMid - inorderLeft)
     */
    private TreeNode buildTree(int[] preorder, int[] inorder, int preorderLeft, int inorderLeft, int inorderRight) {
        TreeNode first = new TreeNode(preorder[preorderLeft]);
        int inorderMid = indexOf(inorder, preorder[preorderLeft]);
//        if (!(inorderMid > inorderLeft && inorderMid < inorderRight)) throw new IllegalArgumentException("can't build");
        if (inorderMid > inorderLeft) {
            first.left = buildTree(preorder, inorder, preorderLeft + 1, inorderLeft, inorderMid - 1);
        }
        if (inorderMid < inorderRight) {
            first.right = buildTree(preorder, inorder, preorderLeft + inorderMid - inorderLeft + 1, inorderMid + 1, inorderRight);
        }
        return first;
    }

    private int indexOf(int[] array, int val) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == val) return i;
        }
        return -1;
    }

    private static void printPreOrderByDeque(TreeNode parent) {
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(parent);
        while (deque.size() > 0) {
            TreeNode node = deque.pollLast();
            if (node == null) continue;
            System.out.print(node.val + " ");
            deque.addLast(node.left);
            deque.addLast(node.right);
        }
    }

    private static void printInOrderByDeque(TreeNode parent) {
        Deque<TreeNode> deque = new LinkedList<>();
        TreeNode cur = parent;
        while (!deque.isEmpty() || cur != null) {
            while (cur != null) {
                deque.addLast(cur);
                cur = cur.left;
            }
            TreeNode node = deque.pollLast();
            System.out.print(node.val + " ");
            if (node.right != null) {
                cur = node.right;
            }
        }
    }

    private static void printInOrder(TreeNode parent) {
        if (parent == null) return;
        printInOrder(parent.left);
        System.out.print(parent.val + " ");
        printInOrder(parent.right);
    }

    /**
     * 层次遍历
     */
    private static void printBfsByDeque(TreeNode parent) {
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(parent);
        while (deque.size() > 0) {
            TreeNode node = deque.pollFirst();
            if (node == null) continue;
            System.out.print(node.val + " ");
            deque.addLast(node.left);
            deque.addLast(node.right);
        }
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
