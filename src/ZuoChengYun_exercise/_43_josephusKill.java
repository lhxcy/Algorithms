package ZuoChengYun_exercise;
/**
 * 目的：环形单链表的约瑟夫问题
 * Created by XCY on 2017/7/14.
 */
public class _43_josephusKill {
    public static class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }
    public Node josephusKill1(Node head, int m){//方法1 时间复杂度O(m*n)
        if (head == null || head.next == null || m < 1){
            return head;
        }
        Node last = head;
        while (last.next != head){
            last = last.next;
        }
        int count = 0;
        while (head != last){
            if (++count == m){
                last.next = head.next;
//                head = head.next;
                count = 0;
            }else {
                last = last.next;
//                head = head.next;
            }
            head = last.next;
        }
        return head;
    }

    public static Node josephusKill2(Node head, int m){//方法2 时间复杂度O(m)
        //设报A的是编号B的节点 B = (A - 1) % i + 1    i为长度
        //长度为i-1 的链表的存活点与长度为i的存活点的编号的关系 old = (new + s -1) % i + 1, s 就是B A为m
        if (head == null || head.next == null || m < 1){
            return head;
        }
        Node cur = head.next;
        int tmp = 1;//链表长度
        while (cur != null){
            tmp++;
            cur = cur.next;
        }
        System.out.println(tmp);
        tmp = getLive(tmp, m);
        System.out.println(tmp);
//        int i = 0;
//        while (++i < tmp){
//            head = head.next;
//            System.out.println(" i " + i);
//            System.out.println(" llll " + head.value);
//        }
        while (--tmp != 0){
            head = head.next;
            System.out.println(" tmp " + tmp);
            System.out.println(" llll " + head.value);
        }
        head.next = head;
        return head;
    }
    public static int getLive(int i, int m){//找到存活节点编号
        if (i == 1){
            return 1;
        }
        return (getLive(i-1, m) + m - 1) % i + 1;
    }

    public static void main(String [] args){
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        Node temp = josephusKill2(head,3);
        System.out.println(temp.value);
    }
}
