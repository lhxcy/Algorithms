package leedcode;
import  java.util.*;
import java.util.PriorityQueue;
//import java.util.Arrays
/**
 * Created by XCY on 2017/5/9.
 */
public class _23_Merge_k_Sorted_Lists {

//      Definition for singly-linked list.
      public class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
      }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode list=new ListNode(0);
        ListNode result=list;
        // result.val=0;
        int len=lists.length;
        if (len==0)
            return null;
        Queue<ListNode> integerPriorityQueue = new PriorityQueue<ListNode>(lists.length,new Comparator<ListNode>(){
            @Override
            public int compare(ListNode o1,ListNode o2){
                if (o1.val<o2.val)
                    return -1;
                else if (o1.val==o2.val)
                    return 0;
                else
                    return 1;
            }
        });

        for (int i =0;i<len;i++){
            if (lists[i]==null){
                System.out.println(1);
                continue;
            }
            while (lists[i]!=null){
                integerPriorityQueue.add(lists[i]);
                lists[i]=lists[i].next;
            }
        }
        int size=integerPriorityQueue.size();
        // System.out.println(integerPriorityQueue);
        if (size==0)
            return null;
        // System.out.println("lllll:");
        for (int i = 0;i<size;i++){
            // System.out.println(i);
            list.next =integerPriorityQueue.poll();
            list=list.next;

        }
        list.next=null;//不写这句会导致内存溢出，因为integerPriorityQueue.poll()最后一部返回的是最后一个元素的地址返回的是

        // while(result.next!=null){
        //     System.out.println(result.val);
        //     result=result.next;
        // }

        return result.next;
    }
}
