package ZuoChengYun_exercise;

import java.util.Stack;

/**
 * 目的：将单链表每k个节点逆序,最后不够则不逆序
 * Created by XCY on 2017/7/19.
 * 1：栈
 * 2：局部逆序
 */
public class _68_reverseKNodes {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node reverseKNodes1(Node head, int k){
        if (k < 2){
            return head;
        }
        Stack<Node> stack = new Stack<Node>();
        Node newHead = head;
        Node cur = head;
        Node pre = null;//记录前一段的前一个节点
        Node nextNode = null;
        while (cur != null){
            nextNode = cur.next;
            stack.push(cur);
            if (stack.size() == k){
                pre = resign1(stack, pre, cur);//链接各部分需要用
                newHead = newHead == null ? cur : newHead;
            }
            cur = cur.next;
        }
        return newHead;
    }

    public Node resign1(Stack<Node> stack, Node left, Node right){//返回反转部分的最后一个节点
        Node cur = stack.pop();
        if (left != null){
            left.next = cur;
        }
        Node nextNode = null;
        while (!stack.isEmpty()){
            nextNode = stack.pop();
            cur.next = nextNode;
            cur = nextNode;
        }
        cur.next = right;
        return cur;
    }

    public Node reverseKNodes2(Node head, int k){
        if (k < 2){
            return head;
        }
        Node cur = head;
        Node start = null;
        Node pre = null;
        Node nextNode = null;
        int count = 1;
        while (cur != null){
            nextNode = cur.next;
            if (count == k){
                start = start == null ? head : pre.next;//需要反转的部分的第一个节点
                head = pre == null ? cur : head;//头结点
                resign2(pre, start, cur, nextNode);
                pre = start;//反转后start变为，需要反转的第一个节点的上一个节点
                count = 0;
            }
            cur = nextNode;
            count++;
        }
        return head;
    }

    public void resign2(Node left, Node start, Node end , Node right){
        Node pre = start;
        Node cur = start.next;
        Node nextNode = null;
        while (cur != null){
            nextNode = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nextNode;
        }
        if (left != null){
            left.next = end;
        }
        start.next = right;
    }
}
