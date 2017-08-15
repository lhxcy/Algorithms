package ZuoChengYun_exercise;
import java.util.Stack;

/**
 * 目的：两个链表生成相加链表
 * Created by XCY on 2017/7/17.
 * 1：栈，逆序相加
 * 2：逆序链表，相加
 */
public class _59_addList {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node addList1(Node head1, Node head2){
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();
        while (head1 != null){
            stack1.push(head1.value);
            head1 = head1.next;
        }
        while (head2 != null){
            stack2.push(head2.value);
            head2 = head2.next;
        }
        int flag = 0;//进位
        int n1 = 0;//stack1 值
        int n2 = 0;//stack2 值
        int n = 0;//局部sum
        Node node = null;//申请新节点
        Node pre = null;
        while (!stack1.isEmpty() || !stack2.isEmpty()){
            n1 = stack1.isEmpty() ? 0 : stack1.pop();
            n2 = stack2.isEmpty() ? 0 : stack2.pop();
            n = n1 + n2 + flag;
            pre = node;
            node = new Node(n % 10);
            node.next = pre;
            flag = n / 10;
        }
        if (flag == 1){
            pre = node;
            node = new Node(1);
            node.next = pre;
        }
        return node;
    }

    public Node addList2(Node head1, Node head2){
        head1 = reverseList(head1);
        head2 = reverseList(head2);
        int flag = 0;
        int n1 = 0;
        int n2 = 0;
        int n = 0;
        Node c1 = head1;
        Node c2 = head2;
        Node node = null;
        Node pre = null;
        while (c1 != null || c2 != null){
            n1 = c1 != null ? c1.value : 0;
            n2 = c2 != null ? c2.value : 0;
            n = n1 + n2 + flag;
            pre = node;
            node = new Node(n % 10);
            node.next = pre;
            flag = n / 10;
            c1 = c1 != null ? c1.next : null;
            c2 = c2 != null ? c2.next : null;
        }
        if (flag == 1){
            pre = node;
            node = new Node(1);
            node.next = pre;
        }
        head1 = reverseList(head1);//reverseList(head1),这样也可以
        head2 = reverseList(head2);
        return node;

    }
    public Node reverseList(Node head){
        Node pre = null;
        Node nextNode = null;
        while (head != null){
            nextNode = head.next;
            head.next = pre;
            pre = head;
            head = nextNode;
        }
        return pre;
    }
}
