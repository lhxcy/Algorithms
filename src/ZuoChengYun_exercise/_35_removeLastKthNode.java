package ZuoChengYun_exercise;
/**
 * 目的：删除链表倒数第k个节点  单向 双向
 * Created by XCY on 2017/7/12.
 * 没有头指针
 */
public class _35_removeLastKthNode {
    public static class Node{
        public int value;
        public Node next;
        public  Node(int data){
            this.value = data;
        }
    }

    public static Node removeLastKthNode(Node head, int lastKth){//单向链表
        if (head == null || lastKth < 1){
            return head;
        }
        Node cur = head;
        while (cur != null){
            lastKth--;
            cur = cur.next;
        }
        if (lastKth == 0){
            return head.next;
        }
        if (lastKth < 0){
            cur = head;
            while (++lastKth != 0){//先加在比较
//                lastKth++;
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }
        return head;
    }

    public static Node removeLastKthNode2(Node head, int lastKth){//自己写的删除倒数kth个节点
        if (head == null || lastKth < 1){
            return head;
        }
        Node first = head;//前行节点
        Node last = head;
//        System.out.println(head.value);
//        while ()
        while (lastKth > 0 && first.next!= null){
            //没有头结点，需要使first与last之间差k个节点，
            // 进而使last指向待删节点的前一个节点
            first = first.next;
            lastKth--;
            System.out.println("first:" + first.value + " lastkth:" + lastKth);
        }
        if (lastKth > 1){//k大于链表长度时
            return head;
        }
        if (first.next == null){//删除第一个元素
            return head.next;
        }
//        System.out.println("first:" + first.value + " last:" + last.value);
        while (first.next!= null){
            last = last.next;
            first = first.next;
//            System.out.println("first:" + first.value + " last:" + last.value);
        }
//        System.out.println("last:" + last.value);
        last.next = last.next.next;

        return head;
    }

    public class DoubleNode{
        public int value;
        public DoubleNode pre;
        public DoubleNode next;
        public DoubleNode(int data){
            this.value = data;
        }
    }

    public DoubleNode removeLastKthDoubleNode(DoubleNode head, int lastKth){
        if (head == null || lastKth < 1){
            return head;
        }
        DoubleNode cur = head;
        while (cur != null){
            lastKth--;
            cur = cur.next;
        }
        if (lastKth == 0 ){
            head = head.next;
            head.pre = null;
        }
        if (lastKth < 0 ){
            cur = head;
            while (lastKth < 0 ){
                lastKth++;
                cur = cur.next;
            }
//            cur.next = cur.next.next;
//            if (cur.next != null){
//                cur.next.pre = cur;
//            }
            DoubleNode tempNode = cur.next.next;
            cur.next = tempNode;
            if (tempNode != null){
                tempNode.pre = cur;
            }

        }
        return head;

    }


    public static void main(String [] args){
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        Node cur = removeLastKthNode2(head,5);
        while (cur != null){
            System.out.print(cur.value+" ");
            cur = cur.next;
        }
    }
}
