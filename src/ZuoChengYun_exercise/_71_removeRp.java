package ZuoChengYun_exercise;

import java.util.HashSet;

/**
 * 目的：删除单链表中值重复出现的节点
 * Created by XCY on 2017/7/19.
 * 1：hash O(n)
 * 2：判断后面是否有与前面值相等的节点 O(n*n)
 */
public class _71_removeRp {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public void removeRp1(Node head){
        if (head == null){
            return ;
        }
        HashSet<Integer> set = new HashSet<Integer>();
        Node pre = head;
        Node cur = head.next;
        set.add(head.value);
        while (cur != null){
            if (set.contains(cur.value)){
                pre.next = cur.next;
            }else {
                set.add(cur.value);
                pre = cur;
            }
            cur = cur.next;
        }
    }

    public void removeRp2(Node head){
        Node cur = head;
        Node pre = null;
        Node nextNode = null;
        while (cur != null){
            pre = cur;
            nextNode = cur.next;
            while (nextNode != null){
                if (cur.value == nextNode.value){
                    pre.next = nextNode.next;
                }else {
                    pre = nextNode;
                }
                nextNode = nextNode.next;
            }
            cur = cur.next;
        }
    }
}
