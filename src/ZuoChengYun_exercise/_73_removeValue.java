package ZuoChengYun_exercise;

import java.util.Stack;

/**
 * 目的：删除链表指定值节点
 * Created by XCY on 2017/7/19.
 * 1:栈
 * 2：直接处理
 */
public class _73_removeValue {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node removeValue(Node head, int num){
        Stack<Node> stack = new Stack<Node>();
        while (head != null){
            if (head.value != num){
                stack.push(head);
            }
            head = head.next;
        }
        while (!stack.isEmpty()){
            stack.peek().next = head;
            head = stack.pop();
        }
        return head;
    }

    public Node removeValue2(Node head, int num){
        while (head != null) {//排除前面几个都等于num的情况
            if (head.value != num) {
                break;
            }
        }
        Node cur = head;
        Node pre = head;
        while (cur != null){
            if (cur.value == num){
                pre.next = cur.next;
            }else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;

    }
}
