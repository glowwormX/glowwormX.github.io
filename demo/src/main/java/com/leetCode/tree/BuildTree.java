package com.leetCode.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class BuildTree {
    public static void main(String[] args) {
        String s = "1{2{3,4{5,6{,7}}},8{9}}";
        Deque<TreeNode> deque = new LinkedList<>();
        TreeNode parent = null;
        try {
            for (int i = 0; i < s.length() - 1; i++) {
                char c = s.charAt(i);
                if (c == '{') {
                    continue;
                }
                if (c == ',') {
                    if (i > 1 && s.charAt(i - 1) == '{') {
                        deque.addLast(null);
                    }
                    continue;
                }
                if (c == '}') {
                    deque.pollLast();
                    continue;
                }
                TreeNode node = new TreeNode(Integer.parseInt(Character.toString(c)));
                if (parent == null) parent = node;
                if (i > 1 && s.charAt(i - 1) == '{') {
                    deque.getLast().left = node;
                } else if (i > 1 && s.charAt(i - 1) == ',') {
                    deque.pollLast();
                    deque.getLast().right = node;
                }
                deque.addLast(node);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        parent.printAll();
    }
}
