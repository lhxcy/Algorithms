package ZuoChengYun_exercise;
/**
 * 目的：重新排列链表 1 2 3 4 5    1 3 2 4 5  ，  1 2 3 4    1 3 2 4
 * Created by XCY on 2017/7/20.
 */
public class _86_relocateList {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public void relocateList(Node head){
        if (head == null || head.next == null){
            return ;
        }
        Node midE = head;
        Node right = head.next;
        while (right.next != null && right.next.next != null){
            midE = midE.next;
            right = right.next.next;
        }
        right = midE.next;//右半部分头结点
        midE.next = null;
        mergeLR(head,right);
    }


    public void mergeLR(Node left, Node right){
        Node nextNode = null;
        while (left.next != null){
            nextNode = right.next;
            right.next = left.next;
            left.next = right;
            left = right.next;
            right = nextNode;
        }
        left.next = right;
    }
}
