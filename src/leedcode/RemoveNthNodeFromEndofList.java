package leedcode;

import static algorithms.Print.print;
import static algorithms.Print.println;

/**
 * Created by XCY on 2017/4/11.
 */
public class RemoveNthNodeFromEndofList {
    private static class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode list=head;
        ListNode first=head;
        ListNode last=head;
        int count=0;
        while (head!=null){//方法1
            count++;
            head=head.next;
        }
        if (count==1)
            return null;
        if(count==n)
            return list.next;
        int k=0;
        while (k<n&&first.next!=null){
            first=first.next;
            k++;
        }
        while(first.next!=null){
            first=first.next;
            last=last.next;
        }
        ListNode temp=last.next;
        last.next=temp.next;
        return list;
 /*       while (head!=null){//方法2
            count++;
            head=head.next;
        }
        if (count==1)
            return null;
        if (count==n)
            return list.next;
        int k=0;
        while (last.next!=null){

            while (k<n&&first.next!=null){
                first=first.next;
                k++;
            }
            if (first.next==null){
                ListNode temp=last.next;
                last.next=temp.next;
                return list;
            }
            first=first.next;
            last=last.next;
        }
        return list;*/

    }
    public static void main(String []args){
        ListNode list=new ListNode(1);
        println(list.val);
//        ListNode temp=list;
////        println(temp.val);
//        list.next=new ListNode(2);
//        list=list.next;
//        list.next=new ListNode(3);
//        list=list.next;
//        list.next=new ListNode(4);
//        list=list.next;
//        list.next=new ListNode(5);
//        list=list.next;
//        list=temp;
//        list.next=new ListNode(5);
//        list=list.next;
//        while (list!=null){
//            print(list.val);
//            list=list.next;
//        }
        ListNode list1=removeNthFromEnd(list,1);
//        println("lllllllll");
        while (list1!=null){
            print(list1.val);
            list1=list1.next;
        }



    }
}
