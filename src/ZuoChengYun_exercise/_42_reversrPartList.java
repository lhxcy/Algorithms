package ZuoChengYun_exercise;
/**
 * 目的：反转部分链表
 * Created by XCY on 2017/7/14.
 */
public class _42_reversrPartList {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node reversePartList(Node head, int from, int to){
        int len = 0;
        Node nodefirst = head;
        Node fpre = null;//需要反转的部分的前一个节点
        Node tPos = null;//需要反转的部分的后一个节点
        while (nodefirst != null){
            len++;
            fpre = len == from-1 ? nodefirst : fpre;
            tPos = len == to + 1 ? nodefirst : tPos;
            nodefirst = nodefirst.next;
        }
        if (from > to || to> len || from < 1){
            return head;
        }
        nodefirst = fpre == null ? head : fpre.next;//nodeFirstReverseNode需要反转的部分的第一个节点
        Node nodenext = nodefirst.next;//需要反转的部分的第二个节点
        nodefirst.next = tPos;//需要反转的部分的第一个节点链接到需要反转的部分的后一个节点
        Node templast = null;
        while (nodenext != tPos){
            templast = nodenext.next;
            nodenext.next = nodefirst;
            nodenext = templast;
            nodefirst = nodenext;
        }
        if (fpre != null){
            fpre.next = nodefirst;//若不为空，需要使 需要反转的部分的前一个节点 链接到 需要反转的部分的最后一个节点
            return head;
        }
        return head;
    }
}
