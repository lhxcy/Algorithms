package leedcode;

/**
 * Created by XCY on 2017/4/25.
 */
public class SwapNodesinPairs {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }

    public class Solution {
        public ListNode swapPairs(ListNode head) {

            ListNode list=new ListNode(0);
            ListNode pre=new ListNode(0);
            ListNode nex=new ListNode(0);
            ListNode prepre=new ListNode(0);
            list =head;
            if(head==null)
                return list;
            pre=head;
            int count=1;
            int countflag=2;
            boolean flag=false;
            while (pre.next!=null){
                nex=pre.next;
                count++;
                if (count==countflag){
                    System.out.println(count);
                    prepre.next=nex;
                    pre.next=nex.next;
                    nex.next=pre;
                    countflag+=2;
                    if(count==2)
                        list=prepre.next;
                    flag=true;
                }
                prepre=pre;//prepre表示当前节点的前一个节点，因为初次肯定交换
                if(flag==false)
                    pre=pre.next;
                else{
                    flag=false;//交换后，pre已经变为nex的位置，因此不需要移动pre
                    // continue;
                }


            }
            return  list;
        }

        }
    }
}
