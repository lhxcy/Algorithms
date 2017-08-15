package algorithms;

import leedcode.add_two_number;
/**
 * Created by XCY on 2017/3/24.
 */
public class Listreverse {
    public static class ListNode{
        int val;
        ListNode next;
        public  ListNode(int x){ val=x;}
//        public ListNode add(int x){
//            ListNode tem=new ListNode(x);
//            this.next=tem;
//            return tem;
//        }

    }
    public static ListNode reverse(ListNode list){

        ListNode front=null;
        ListNode nex;
        while (list!=null){
            nex=list.next;
            list.next=front;
            front=list;
            list=nex;
        }
        list=front;
        return list;
    }
}
