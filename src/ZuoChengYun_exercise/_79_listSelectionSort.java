package ZuoChengYun_exercise;
/**
 * 目的：链表选择排序
 * Created by XCY on 2017/7/20.
 */
public class _79_listSelectionSort {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public static Node listSelectionSort(Node head){
        Node tail = null;//排序的尾部
        Node cur = head;//未排序的头部
        Node smallPre = null;//最小节点的前一个节点
        Node small = null;//最小节点
        while (cur != null){
            small = cur;
            smallPre = getSmallLestPreNode(cur);
            if (smallPre != null){
                small = smallPre.next;
                smallPre.next = small.next;
            }
            cur = cur == small ? cur.next : cur;
            if (tail == null){
                head = small;
            }else {
                tail.next = small;
            }
            tail = small;
        }
        return head;
    }

    public static Node getSmallLestPreNode(Node head){//得到最小节点的前一个节点
        Node smallPre = null;
        Node small = head;
        Node pre = head;
        Node cur = head.next;
        while (cur != null){
            if (cur.value < small.value){
                smallPre = pre;
                small = cur;
            }
            pre = cur;
            cur = cur.next;
        }
        return smallPre;
    }
}
