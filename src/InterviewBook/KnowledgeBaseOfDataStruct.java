package InterviewBook;

/**
 * Created by XCY on 2017/12/22.
 */
public class KnowledgeBaseOfDataStruct {
}

/*
1: 编程实现单链表的建立/测长/打印
 */
class ListNode{
    public int value;
    public ListNode next;
    public ListNode(int value){this.value = value;}
}
class OperationOfListNode{
    public static void main(String[] args){
        int[] arr = {1,2,3,4,5};
        ListNode head = creatList(arr);
        int len = listNodelength(head);
        System.out.println(len);
        printListNode(head);
    }
    public static ListNode creatList(int[] arr){
        if (arr == null || arr.length == 0)
            return null;
        ListNode head = null;
        ListNode tail = null;
        ListNode tempNode;
        int len = 0;
        head = new ListNode(arr[len++]);
        tail = head;
        while (len < arr.length){
            tempNode = new ListNode(arr[len++]);
            tail.next = tempNode;
            tail = tail.next;
        }
        return head;
    }

    public static void printListNode(ListNode head){
        System.out.println("prin ListNode");
        if (head != null){
            while (head != null){
                System.out.print(head.value + " ");
                head = head.next;
            }
            System.out.println();
        }
    }

    public static int listNodelength(ListNode head){
        if (head != null){
            int lenght = 0;
            while (head != null){
                ++lenght;
                head = head.next;
            }
            return lenght;
        }
        return -1;
    }
}


/*
2: 删除指定值的节点
 */
class RemoveNodeOfListNode{
    public static void main(String[] args){
        int[] arr = {1,2,3,4,5};
        ListNode head = OperationOfListNode.creatList(arr);
        System.out.print("before: ");
        OperationOfListNode.printListNode(head);
        System.out.print("after: ");
        ListNode rhead = removeNode(head,5);
        OperationOfListNode.printListNode(rhead);
    }
    public static ListNode removeNode(ListNode head, int value){
        if (head != null){
            if (head.value == value){
                head = head.next;
                return head;
            }
            ListNode tempNode = head;
            while (tempNode.next != null){
                if (tempNode.next.value != value){
                    tempNode = tempNode.next;
                }else {
                    tempNode.next = tempNode.next.next;
                }
            }
            return head;
        }
        return null;
    }
}


/*
3: 将节点插入最后一个比它小的节点后面
 */
class InsertListNode{
    public static void main(String[] args){
        int[] arr = {1,3,5,7,9};
        ListNode head = OperationOfListNode.creatList(arr);
        System.out.print("before: ");
        OperationOfListNode.printListNode(head);
        ListNode ihead = insertNode(head, 10);
        System.out.print("after: ");
        OperationOfListNode.printListNode(ihead);

    }
    public static ListNode insertNode(ListNode head, int value){
        if (head != null){
            ListNode newNode = new ListNode(value);
            ListNode tempNode = head;
            while (tempNode.next != null){
                if (tempNode.value <= value && tempNode.next.value > value){
                    newNode.next = tempNode.next;
                    tempNode.next = newNode;
                    return head;
                }
                tempNode = tempNode.next;
            }
            tempNode.next = newNode;
            return head;
        }
        return null;
    }
}



/*
4： 编程实现单链表排序
 */
class SortListNode{
    public static void main(String[] args){
        int[] arr = {3,6,2,4,1};
        ListNode head = OperationOfListNode.creatList(arr);
        OperationOfListNode.printListNode(head);
        head = sort(head);
        OperationOfListNode.printListNode(head);
    }
    public static ListNode sort(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode flagListNode = head;
        ListNode tempListNode;
        int temp;
        while (flagListNode != null){
            temp = flagListNode.value;
            tempListNode = flagListNode.next;
            while (tempListNode != null){
                if (temp > tempListNode.value){
                    flagListNode.value = tempListNode.value;
                    tempListNode.value = temp;
                    temp = flagListNode.value;
                }
                tempListNode = tempListNode.next;
            }
            flagListNode = flagListNode.next;
        }
        return head;
    }
}


/*
5: 实现单链表的逆置
 */
class ReverseListNode{
    public static void main(String[] args){
        int[] arr = {1,2,3,4,5};
//        int[] arr = {1};
        ListNode head = OperationOfListNode.creatList(arr);
        OperationOfListNode.printListNode(head);
        ListNode reverse = reverseListNode(head);
        OperationOfListNode.printListNode(reverse);
    }
    public static ListNode reverseListNode(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode pre = null;
        ListNode tail;
        while (head != null){
            tail = head.next;
            head.next = pre;
            pre = head;
            head = tail;
        }
        return pre;
    }
}


/*
7：给出一个单链表，不知道节点N的值，怎样只遍历一次就可以求出中间节点
思路：两个节点p、q，p每次移动一个位置，即 p = p.next，q每次移动两个位置，即q = q.next.next，当q到达最后一个节点，
p就是中间节点。
 */

class FindMidNode{
    public static void main(String[] args){
        int[] arr = {1,2,3,4,5};
        ListNode head = OperationOfListNode.creatList(arr);
        OperationOfListNode.printListNode(head);
        ListNode midNode = midNode(head);
        System.out.println(midNode.value);
    }
    public static ListNode midNode(ListNode head){
        if (head == null)
            return null;
        ListNode pre = head;
        ListNode pos = head;
        while (pos != null && pos.next != null){
            pre = pre.next;
            pos = pos.next.next;
        }
        return pre;
    }
    public static ListNode midNode1(ListNode head){
        if (head == null)
            return null;
        ListNode pre = head;
        while (head.next.next !=null){
            head = head.next.next;
            pre = pre.next;
        }
        return pre;
    }
}


/*
双链表
8：编程实现双链表的建立、删除节点、插入节点
 */
class BiListNode{
    public int value;
    public BiListNode pre;
    public BiListNode next;
    public BiListNode(int value){this.value = value;}
}
class OperationBiListNode{
    public static BiListNode creatDiList(int[] arr){
        if (arr == null || arr.length == 0)
            return null;
        BiListNode head = null;
        BiListNode tail = null;
        BiListNode tempNode;
        int len = 0;
        head = new BiListNode(arr[len++]);
        tail = head;
        while (len < arr.length){
            tempNode = new BiListNode(arr[len++]);
            tail.next = tempNode;
            tempNode.pre = tail;
            tail = tail.next;
        }
        return head;
    }

    public static BiListNode deleteDiNode(BiListNode head, int num){
        if (head == null)
            return head;
        BiListNode tempDiNode = head;
        while (head.value != num && head.next != null){
            tempDiNode = tempDiNode.next;
        }
        if (num == tempDiNode.value){
            if (tempDiNode == head){
                head = head.next;
                head.pre = null;
            }else if (tempDiNode.next == null){
                tempDiNode.pre.next = null;
            }else {
                tempDiNode.next.pre = tempDiNode.pre;
                tempDiNode.pre.next = tempDiNode.next;
            }
        }else {
            System.out.printf("%d could not found",num);
        }
        return head;
    }

    public static BiListNode insertDiNode(BiListNode head, int num){
        //找到合适的位置插入num节点，前一个节点的值小于num
        BiListNode tempDiNode = new BiListNode(num);
        BiListNode BiListNode = head;
        if (head == null){
            head = tempDiNode;
        }
        while (BiListNode.value > num && BiListNode.next != null){
            BiListNode = BiListNode.next;
        }
        if (num <= BiListNode.value){
            if (head == BiListNode){
                tempDiNode.next = head;
                head.pre = tempDiNode;
                head = tempDiNode;
            }else{
                BiListNode.pre.next = tempDiNode;
                tempDiNode.next = BiListNode;
                tempDiNode.pre = BiListNode.pre;
                BiListNode.pre = tempDiNode;
            }
        }else {
            BiListNode.next = tempDiNode;
            tempDiNode.pre = BiListNode;
            tempDiNode.next = null;
        }
        return head;
    }
}

class test{
    public static void main(String[] args){
        int[] arr = {1,1,2,2,3,3,4,4};
        ListNode head = OperationOfListNode.creatList(arr);
        OperationOfListNode.printListNode(head);
        deleteDuplicationListNode(head);
        OperationOfListNode.printListNode(head);
    }
    public static void deleteDuplicationListNode(ListNode head){
        if (head == null)
            return;
        ListNode pre = head;
        ListNode tempNode = head;
        while (tempNode != null){
            if (pre.value == tempNode.value){
                pre.next = tempNode.next;
            }else {
                pre = tempNode;
            }
            tempNode = tempNode.next;
        }
    }
}



