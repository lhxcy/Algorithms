package ZuoChengYun_exercise;
/**
 * 目的：将链表从左到右改为小于、等于、大于pivot的节点
 * Created by XCY on 2017/7/17.
 * 1：转变为数组，三路快速排序，链接
 * 2：三个链表，然后连接
 */
public class _52_listPartition {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node listPartition1(Node head, int pivot){
        if (head == null){
            return head;
        }
        Node cur = head;
        int i = 0;
        while (cur != null){
            i++;
            cur = cur.next;
        }
        Node [] nodeArr = new Node[i];
        i = 0;
        cur = head;
        for (i = 0; i != nodeArr.length; i++){//<
            nodeArr[i] = cur;
            cur = cur.next;
        }
        arrPartition(nodeArr, pivot);
        for (i = 1; i != nodeArr.length; i++){
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        return nodeArr[0];
    }

    public void arrPartition(Node [] nodeArr, int pivot){//三路快排
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big){
            if (nodeArr[index].value < pivot){
                swap(nodeArr, ++small, index++);
            }else if (nodeArr[index].value == pivot){
                index++;
            }else {
                swap(nodeArr, --big, index++);
            }
        }
    }

    public void swap(Node [] nodeArr, int a, int b){
        Node temp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = temp;
    }

    public static Node listPartition2(Node head, int pivot){
        Node sH = null;//小的头
        Node sT = null;//小的尾
        Node eH = null;//相等头
        Node eT = null;//相等尾
        Node bH = null;//大的头
        Node bT = null;//大的尾
        Node nextNode = null;//记录下一个节点
        while (head != null){
            nextNode = head.next;
            head.next = null;
            if (head.value < pivot){
                if (sH == null){
                    sH = head;
                    sT = head;
                }else {
                    sT.next = head;
                    sT = head;//尾插法
                }
            }else if (head.value == pivot){
                if (eH == null){
                    eH = head;
                    eT = head;
                }else {
                    eT.next = head;
                    eT = head;
                }
            }else {
                if (bH == null){
                    bH = head;
                    bT = head;
                }else {
                    bT.next = head;
                    bT = head;
                }
            }
            head = head.next;
        }
        if (sT != null){
            sT.next = eH;
            eT = eT == null ? sT : eT;
        }
        if (eT != null){
            eT.next = bH;
        }
        return sH != null ? sH : eH != null ? eH : bH;
    }
}
