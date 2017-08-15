package ZuoChengYun_exercise;
import java.util.HashMap;

/**
 * 目的：复制链表
 * Created by XCY on 2017/7/17.
 * 1：利用hash
 * 2：直接在原节点后面创建新节点，然后处理
 */
public class _56_copyListWithRand {
    public class Node{
        public int value;
        public Node next;
        public Node rand;
        public Node(int data){
            this.value = data;
        }
    }

    public Node copyListWithRand1(Node head){//使用hash完成复制
        HashMap<Node, Node> map = new HashMap<Node, Node>();
        Node cur = head;
        while (cur != null){
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        cur = head;
        while (cur != null){
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }

    public Node copyListWithRand2(Node head){
        if (head == null){
            return null;
        }
        Node cur =head;
        Node nextNode = null;
        while (cur != null){//复制节点并将新节点加入cur和nextNode中间
            nextNode = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = nextNode;
            cur = nextNode;
        }
        cur = head;
        Node curCopy = null;
        while (cur != null){
            nextNode = cur.next.next;
            curCopy = cur.next;
            curCopy.rand = cur.rand != null ? cur.rand.next : null;
            cur = nextNode;
        }
        Node res = head.next;
        cur =head;
        while (cur != null){
            nextNode = cur.next.next;
            curCopy = cur.next;
            cur.next = nextNode;
            curCopy.next = nextNode != null ? nextNode.next : null;
            cur = cur.next;
        }
        return res;
    }
}
