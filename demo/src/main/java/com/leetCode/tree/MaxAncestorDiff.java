package com.leetCode.tree;

import java.util.*;

/**
 * @author 徐其伟
 * @Description:
 * @date 19-8-23 上午8:58
 */
public class MaxAncestorDiff {
    public static void main(String[] args) {
        int[] preorder = {8,1,5,2,7,3,4,6,0};
        int[] inorder = {8,7,2,3,5,4,1,0,6};
//        int[] preorder = {8,3,1,6,4,7,10,14,13};
//        int[] inorder = {1,3,4,6,7,8,10,13,14};
//        int[] preorder = {1,2,0,3};
//        int[] inorder = {1,2,0,3};
        TreeNode treeNode = new PreInOrderBuildTree().buildTree(preorder, inorder);
        System.out.println(new MaxAncestorDiff().maxAncestorDiff1(treeNode));
    }

    //自己的思路：记住路上的正数和负数的差值
    public int maxAncestorDiff(TreeNode root) {
        Map<TreeNode, List<Integer>> map = new HashMap<>();
        List<Integer> init = Arrays.asList(0);
        map.put(root, new ArrayList<>(init));
        subRootAndChild(map, root, root.left);
        subRootAndChild(map, root, root.right);
//        Optional<Integer> max = map.entrySet().stream().map(Map.Entry::getValue).max(Comparator.comparing(Math::abs));
        int max = 0;
        for (Map.Entry<TreeNode, List<Integer>> entry : map.entrySet()) {
            for (Integer v : entry.getValue()) {
                max = Math.max(Math.abs(v), max);
            }
        }
        return max;
    }

    private void subRootAndChild(Map<TreeNode, List<Integer>> map, TreeNode root, TreeNode child){
        if (child != null) {
            List<Integer> value = new ArrayList<>();
            int current = root.val - child.val;
            int min=0, max=0;
            for (Integer parent : map.get(root)) {
                if (parent + current > 0){
                    max = Math.max(parent + current, max);
                } else {
                    min = Math.min(parent + current, min);
                }
            }
            value.add(max);
            value.add(min);
            map.put(child, value) ;
            subRootAndChild(map, child, child.left);
            subRootAndChild(map, child, child.right);
        }
    }


    //其他人思路：找路上的最大最小值即可
    public int maxAncestorDiff1(TreeNode root) {
        return help(root, root.val, root.val);
    }

    public int help(TreeNode root, int min, int max){
        if(root == null)
            return 0;
        int a = Math.max(Math.abs(root.val-min), Math.abs(root.val-max));
        if(root.val < min)
            min = root.val;
        if(root.val > max)
            max = root.val;
        int b = Math.max(help(root.left, min, max), help(root.right, min, max));
        return Math.max(a, b);
    }

}
