package ZuoChengYun_exercise;
/**
 *
 * Created by XCY on 2017/7/12.
 */
public class _38_removeNode {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node removeMidNode(Node head){//删除链表中间节点
        if (head == null || head.next == null){
            return head;
        }
        if (head.next.next == null){
            return head.next;
        }
        Node pre = head;//指向待删节点的前一个节点
        Node cur = head.next.next;
        while (cur.next != null && cur.next.next != null){
            cur = cur.next.next;//pre走一步，cur走两步，始终使pre位于待删节点的前一个
            pre = pre.next;
        }
        pre.next = pre.next.next;
        return head;
    }

    public Node removeByRatio(Node head, int a, int b){
        if (a < 1 || a > b || head == null){
            return head;
        }
        int n = 0;
        Node cur = head;
        while (cur != null){
            n++;
            cur = cur.next;
        }
        n = (int)Math.ceil((n * a )/(double)b);
        if (n == 1){
            head = head.next;
        }
        if (n > 1){
            cur =head;
            while (--n != 1){//因为没有头结点，所以循环条件为--n != 1
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }
        return head;
    }
}
