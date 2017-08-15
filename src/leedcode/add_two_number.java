package leedcode;

import static algorithms.Print.*;

/**
 * Created by XCY on 2017/3/23.
 */
public  class add_two_number {
    public static class ListNode{
        int val;
        ListNode next;
       public  ListNode(int x){ val=x;}
        public  ListNode(){ }
        public ListNode add(int x){
            ListNode tem=new ListNode(x);
            this.next=tem;
            return tem;
        }
        public void lprint(ListNode l1){
             while(l1!=null){
                print(l1.val);
                l1=l1.next;
                }
//            println(false);
        }

    }
//    public static ListNode reverse(ListNode list){
//
//        ListNode front=null;
//        ListNode nex;
//        while (list!=null){
//            nex=list.next;
//            list.next=front;
//            front=list;
//            list=nex;
//        }
//        list=front;
//        return list;
//    }
    public static  ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        l1=reverse(l1);
//        l2=reverse(l2);

        ListNode real=l1;//记录l1
//        ListNode list=l1;
        ListNode ltmp1=l1;//存放l1的前节点
//        ListNode temp2=l1;//存放

        boolean flag=false;
        while (l1!=null&&l2!=null){
            if(flag==false)
                l1.val=l1.val+l2.val;
            else {
                l1.val=l1.val+l2.val+1;
                flag=false;
            }
            if(l1.val>=10){
                l1.val-=10;
                flag=true;
            }

            ltmp1 = l1;
            l1=l1.next;
            l2=l2.next;

        }
        if (l2!=null){
            ltmp1.next=l2;
            l1=ltmp1.next;
            l2=null;
        }
        if(l1!=null)
        {
            while(flag&&l1!=null){
                l1.val+=1;
                flag=false;
                if(l1.val>=10){
                    flag=true;
                    l1.val-=10;
                }

                l1=l1.next;

            }
//            if(flag)
//                l1.val+=1;
        }
//        if(l2!=null){
//            temp2=l2;
//            while (flag&&l2!=null){
//                l2.val+=1;
//                flag=false;
//                if(l2.val>=10){
//                    l2.val-=10;
//                    flag=true;
//                }
//
//                l2=l2.next;
//            }
//            ltmp1=temp2;
////            if(flag){
////                l2.val+=1;
////                ltmp1.next=l2;
////            }
////            else
////                ltmp1.next=l2;
//        }
        if (flag&&l1==null&&l2==null) {
            ListNode tail = real;
            while (true) {
//                list=tail;
                if (tail.next == null) {
                    tail.next = new ListNode(1);
                    break;
                }
                tail=tail.next;

            }

        }

        return  real;


//

    }
    public static void main(String [] args){

        ListNode  l1=new ListNode(5);
////        println(l1.val);
//        ListNode p=l1;
//        l1= l1.add(8);
////        l1= l1.add(3);
//        l1=p;
        ListNode l2=new ListNode(5);
//        ListNode q=l2;
//        l2=l2.add(9);
////        l2=l2.add(4);
//        l2=q;
//////
//        l1=reverse(l1);
//        l1.lprint(l1);
//        l1=reverse(l1);
//        println();
//        l2=reverse(l2);
//        l2.lprint(l2);
//        l2=reverse(l2);
//        println();
        ListNode l3=addTwoNumbers(l1,l2);
//        l3=reverse(l3);

        while(l3!=null){
            print(l3.val);
            l3=l3.next;
        }


    }
}
