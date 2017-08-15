package ZuoChengYun_exercise;
import java.util.Stack;

/**
 * 目的：判断链表是否是回文
 * Created by XCY on 2017/7/17.
 * 1：直接装入栈，再匹配
 * 2：装右半部分入栈，匹配
 * 3：对右半部分逆序，保存右半部分左半部分开头，匹配，复原
 */
public class _48_isPalindrome {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public boolean isPalindrome1(Node head){
        Stack<Node> stack = new Stack<Node>();
        Node cur = head;
        while (cur != null){
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null){
            if (head.value != stack.pop().value){
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public boolean isPalindrom2(Node head){
         if (head == null || head.next == null){
             return true;
         }
         Node right = head.next;//中间靠右第一个节点
         Node cur = head;//表示是否到链表结尾
        while (cur.next != null || cur.next.next != null){
            right = right.next;
            cur = cur.next.next;
        }
        Stack<Node> stack = new Stack<Node>();//记录链表右半部分
        while (right != null){
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()){
            if (head.value != stack.pop().value){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome3(Node head){
        if (head == null || head.next == null){
            return true;
        }
        Node n1 = head;
        Node n2 = head;
        while (n2.next != null || n2.next.next != null){//查找中间节点
            n1 = n1.next;//中间节点
            n2 = n2.next.next;//判断链表是否到达尾部
        }
        n2 = n1.next;//右半部分第一个节点
        n1.next = null;
        Node n3 = null;
        while (n2 != null){//反转右半部
            n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }
        n3 = n1;//保存右半部最后一个节点
        n2 = head;//左半部第一个节点
        boolean res = true;
        while (n1 != null || n2 != null){//判断
            if (n1.value != n2.value){
                res = false;
                break;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        n1 = n3.next;
        n3.next = null;
        while (n1 != null){//还原链表
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }
        return res;
    }
}
