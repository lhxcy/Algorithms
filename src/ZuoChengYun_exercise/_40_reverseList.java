package ZuoChengYun_exercise;
/**
 * 目的：反转链表
 * Created by XCY on 2017/7/14.
 */
public class _40_reverseList {
    public class Node {
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node reverseList(Node head){
        if (head == null || head.next == null){
            return head;
        }
        Node first = null;
        Node pre = null;
        while (head != null){
            first = head.next;
            head.next = pre;
            pre = head;
            head = first;
        }
        return pre;
    }

    public class DoubleNode{
        public int value;
        public DoubleNode next;
        public DoubleNode pre;
        public DoubleNode(int data){
            this.value = data;
        }
    }
    public DoubleNode reverseDoubleNodeList(DoubleNode head){
        if (head == null || head.next == head){
            return head;
        }
        DoubleNode fpre = null;
        DoubleNode fnex = null;
        while (head !=null){
            fnex = head.next;
            head.next = fpre;
            head.pre = fnex;
            fpre = head;
            head = fnex;
        }
        return fpre;
    }
}
