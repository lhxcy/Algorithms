package leedcode;

/**
 * Created by XCY on 2017/4/13.
 */
public class MergeTwoSortedLists {
     private static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
         if (l1==null&&l2==null)
             return null;
         ListNode temp=new ListNode(0);
         ListNode list=temp;
         while (l1!=null&&l2!=null){
             if (l1.val<l2.val){
                 temp.next=l1;
                 temp=temp.next;
                 l1=l1.next;
             }
             else {
                 temp.next=l2;
                 temp=temp.next;
                 l2=l2.next;
             }

         }
         if (l1.next!=null) temp.next=l1;
         if (l2.next!=null) temp.next=l2;

         list=list.next;

         return list;

    }
}
