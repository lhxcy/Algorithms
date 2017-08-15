package ZuoChengYun_exercise;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 目的：将搜索二叉树转化为双向链表
 * Created by XCY on 2017/7/19.
 * 1：队列
 * 2：递归
 */
public class _74_treeConvert {
    public class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int data){
            this.value = data;
        }
    }

    public Node convert1(Node head){
        Queue<Node> queue = new LinkedList<Node>();
        inOrderToQueue(head,queue);
        if (queue.isEmpty()){
            return head;
        }
        head = queue.poll();
        Node pre = head;
        pre.left = null;
        Node cur = null;
        while (!queue.isEmpty()){
            cur = queue.poll();
            pre.right = cur;
            cur.left = pre;
            pre = cur;
        }
        pre.right = null;
        return head;
    }

    public void inOrderToQueue(Node head, Queue<Node> queue){
        if (head == null){
            return;
        }
        inOrderToQueue(head.left,queue);
        queue.offer(head);
        inOrderToQueue(head.right,queue);
    }


    public Node process(Node head){
        //返回的是整个双链表的最后一个节点，最后一个节点的right指针指向第一个节点
        if (head == null){
            return null;
        }
        Node leftE = process(head.left);//左边结束
        Node rightE = process(head.right);//右边结束
        Node leftS = leftE != null ? leftE.right : null;//左边开始
        Node rightS = rightE != null ? rightE.right : null;
        if (leftE != null && rightE != null){//各种情况的连接
            leftE.right = head;
            head.left = leftE;
            head.right = rightS;
            rightS.left = head;
            rightE.right = leftS;
            return rightE;
        }else if (leftE != null){
            leftE.right = head;
            head.left = leftE;
            head.right = leftS;
            return head;
        }else if (rightE != null){
            head.right = rightS;
            rightS.left = head;
            rightE.right = head;
            return rightE;
        }else {
            head.right = head;
            return head;
        }
    }
}
