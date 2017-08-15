package ZuoChengYun_exercise;
/**
 * 目的：向有序环形链表插入新节点
 * Created by XCY on 2017/7/20.
 */
public class _82_insertNum {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node insertNum(Node head, int num){
        Node node = new Node(num);
        if (head == null){
            node.next = node;
            return node;
        }
        Node pre = head;
        Node cur = head.next;
        while (cur != head){
            if (pre.value <= num && cur.value >= num){
                break;
            }
            pre = cur;
            cur = cur.next;
        }
        pre.next = node;//无论是怎么退出的，都需执行这两句，所以不用判断if (cur == head)什么的
        node.next = cur;
        return head.value < num ? head : node;//若num < head.value 返回新节点，新节点插在head之前
    }
}
