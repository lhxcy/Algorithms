package ZuoChengYun_exercise;

/**
 * 目的：若两个链表相交，返回第一个交点，不相交，返回null
 * Created by XCY on 2017/7/17.
 * 有环、无环
 * 有环的和无环的肯定不相交
 */
public class _62_twoListProblem {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public Node getLoopNode(Node head){//有环返回第一个进入环的节点，无环返回null
        if (head == null || head.next == null || head.next.next == null){
            return null;
        }
        Node node1 = head.next;//慢节点
        Node node2 = head.next.next;//快捷点
        while (node1 != node2){
            if (node2.next == null || node2.next.next == null){
                return null;
            }
            node1 = node1.next;
            node2 = node2.next.next;
        }
        node2 = head;//node2 work again from head
        while (node1 != node2){
            node1 = node1.next;
            node2 = node2.next;
        }
        return node1;
    }

    public Node noLoop(Node head1, Node head2){//判断两个无环链表有没有相交
        if (head1 == null || head2 == null){
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null){
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null){
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2){
            return null;
        }
        cur1 = n > 0 ? head1 : head2;//cur1 指向长的链表
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n != 0){
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2){
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    public Node reverseList(Node head){//reverse list
        Node pre = null;
        Node nextNode = null;
        while (head != null){
            nextNode = head.next;
            head.next = pre;
            pre = head;
            head = nextNode;
        }
        return pre;
    }

    public Node noLoop2(Node head1, Node head2){//利用逆转链表，自己写的
        head1 = reverseList(head1);
        head2 = reverseList(head2);
        if (head1 != head2){
            return null;
        }
        Node node = head1;
        Node node1 = head1.next;
        Node node2 = head2.next;
        while ((node1 != null && node2 != null) && (node1 == node2) ){
            node = node1;
            node1 = node1.next;
            node2 = node2.next;
        }
        head1 = reverseList(head1);
//        head2 = reverseList(head2);//可以不要
        if (node1 == null || node2 == null){
            return head1;
        }
        return node;
    }

    public Node bothLoop(Node head1, Node loop1, Node head2, Node loop2){//对两个都有还的链表处理
        Node cur1 = null;
        Node cur2 = null;
        if (loop1 == loop2){
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1){//因为loop1==loop2，所以可以不用cur1.next
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2){
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0){
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2){
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }else {
            cur1 = loop1.next;
            while (cur1 != loop1){
                if (cur1 == loop2){
                    return loop1;//loop2也可以,证明在环内相交
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    public Node getIntersectNode(Node head1, Node head2){
        if (head1 == null || head2 == null){
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null){
            return noLoop(head1, head2);
        }else if (loop1 != null && loop2 != null){
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

}
