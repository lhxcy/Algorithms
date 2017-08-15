package ZuoChengYun_exercise;
/**
 * 目的：输出两个有序链表的共有部分
 * Created by XCY on 2017/7/12.
 *判断两个链表元素是否相同
 */
public class _34_printCommonPart {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public void printCommonPart(Node head1, Node head2){
        System.out.print("Common Part:");
        while (head1 !=null && head2 != null){
            if (head1.value < head2.value){
                head1 = head1.next;
            }else if (head1.value > head2.value){
                head2 = head2.next;
            }else {
                System.out.print(head1.value+" ");
                head1 = head1.next;
                head2 = head2.next;
            }
        }
        System.out.println();
    }
}
