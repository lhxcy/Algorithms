package ZuoChengYun_exercise;

/**
 * Created by XCY on 2017/7/20.
 */
public class _84_mergeSortedList {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node mergeSortedList(Node head1, Node head2){
        if (head1 == null || head2 == null){
            return head1 != null ? head1 : head2;
        }
        Node head = head1.value < head2.value ? head1 : head2;
        Node cur1 = head == head1 ? head1 : head2;//small
        Node cur2 = head == head1 ? head2 : head1;//big
        Node pre = null;
        Node nextNode = null;
        while (cur1 != null && cur2 != null){
            if (cur1.value < cur2.value){
                pre = cur1;///
                cur1 = cur1.next;
            }else {
                nextNode = cur2.next;
                pre.next = cur2;
                cur2.next = cur1;//保证当cur1.value < cur2.value时，不断链，
                // 因为在该情况直接令pre = cur1，可以改写为其他方法
                pre = cur2;
                cur2 = nextNode;
            }
        }
        pre.next = cur1 != null ? cur1 : cur2;
        return head;
    }
}
