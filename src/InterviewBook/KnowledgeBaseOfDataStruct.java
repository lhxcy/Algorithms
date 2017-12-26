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

}



