package ZuoChengYun_exercise;

import java.util.Stack;

/**
 * 目的：二叉树遍历
 * Created by XCY on 2017/7/20.
 * 前、中、后
 * 递归、非递归
 */
public class _88_BiTreeOrderRecur {
    public class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int data){
            this.value = data;
        }
    }

    public void preOrderRecur(Node head){//先序
        if (head == null){
            return;
        }
        System.out.print(head.value + " ");
        preOrderRecur(head.left);
        preOrderRecur(head.right);
    }

    public void inOrderRecur(Node head){//中序
        if (head == null ){
            return;
        }
        inOrderRecur(head.left);
        System.out.print(head.value + " ");
        inOrderRecur(head.right);
    }

    public void posOrderRecur(Node head){//后序
        if (head == null){
            return;
        }
        posOrderRecur(head.left);
        posOrderRecur(head.right);
        System.out.print(head.value + " ");
    }

    public void preOrderUnRecur(Node head){//非递归前序
        System.out.print("pre-order:");
        if (head != null){
            Stack<Node> stack = new Stack<Node>();
            stack.add(head);
            while (!stack.isEmpty()){
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null){
                    stack.push(head.right);
                }
                if (head.left != null){
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    public void inOrderUnRecur(Node head){
        System.out.print("in-order:");
        if (head != null){
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || head != null){
                if (head != null){
                    stack.push(head);
                    head = head.left;
                }else {
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }

    public void posOrderUnRecur1(Node head){
        System.out.print("pos-order:");
        if (head != null){
            Stack<Node> stack1 = new Stack<Node>();
            Stack<Node> stack2 = new Stack<Node>();//最终的顺序
            stack1.push(head);
            while (!stack1.isEmpty()){
                head = stack1.pop();
                stack2.push(head);
                if (head.left != null){
                    stack1.push(head.left);
                }
                if (head.right != null){
                    stack1.push(head.right);
                }
            }
            while (!stack2.isEmpty()){
                System.out.print(stack2.pop().value + "");
            }
        }
        System.out.println();
    }

    public void posOrderUnRecur2(Node head){
        System.out.print("pos-order;");
        if (head != null){
            Stack<Node> stack = new Stack<Node>();
            stack.push(head);
            Node peekNode = null;
            while (!stack.isEmpty()){
                peekNode = stack.peek();
                if (peekNode.left != null && head != peekNode.left && head != peekNode.right){
                    stack.push(peekNode.left);
                }else if (peekNode.right != null && head != peekNode.right){
                    stack.push(peekNode.right);
                }else {
                    System.out.print(stack.pop().value + " ");
                    head = peekNode;
                }
            }
        }
        System.out.println();
    }

}
