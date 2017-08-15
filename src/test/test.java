package test;

import java.util.ArrayList;
import java.util.List;

import leedcode.add_two_number;
import leedcode.add_two_number.*;
//
import java.lang.reflect.Array;
import java.util.*;

import static algorithms.Print.*;

/**
 * Created by XCY on 2017/3/24.
 */
///////////////////////////////
public class test {
    private static void sortID(int [] a){
//        double p=1.0/32;
//        double sum=0.0;
//        for (int i=0;i<32;i++){
//
//            sum+=p*(Math.log(p)/Math.log(2));
//        }
//        print(-sum);
//

//        int alen=a.length;

//        SortedSet<Integer> sortedSet=new TreeSet<>();
////        Collection.addAll(sortedSet,a);
////        TreeSet hash=new TreeSet();
////        Map hash=new HashMap();
//        int count=0;
//        for(int i=0;i<alen-1;i++){
//            if (a[i]==0&&count!=1){
//                count++;
//                continue;
//            }
//            else {
//                sortedSet.add(a[i]);
//            }
//
//        }
////        print(sortedSet);
//        Integer [] b=sortedSet.toArray(new Integer[0] );
//        int len=b.length;
//
//        for (int i=0;i<len;i++)
//            print(b[i]);
//        int alen=a.length;
//        Map map=new HashMap();
//        int count=0;
//        for(int i=0;i<alen-1;i++){
//            if (a[i]==0&&count!=1){
//                count++;
//                continue;
//            }
//            else {
//                if (!map.containsKey(a[i]))
//                    map.put(a[i],i);
//            }
//
//        }


//
//        Map map=new HashMap();
//        Object []b=new Object [map.size()];
////        int j=0;
//        Set set =map.keySet();
//        b=set.toArray(new Integer[0]);
//        for(int i=0;i<map.size();i++)
//            print(b[i]);
//        Iterator iter = set.iterator();
//        while (iter.hasNext()){
//           b[j++] =map.get(iter.next());
//           print(b[j-1]);}

////
//
//
//        }
////        for (int i=alen;i<blen+alen;i++){
//            hash.put(i,bsource[i]);
//        }


//    }
//    public static void switch_ele(int a,int b){
//        int temp=a;
//        a=b;
//        b=temp;
//
    }
    public static void Permutations(int [] nums, int left, int right){
        if (left == right){
            for (int i = 0; i < nums.length; i++){
                print(nums[i] + " ");
            }
            println();
        }else {
            for (int i = left; i <= right; i++){//因为传进来参数为长度减1，所以此时需要等号
                swap(nums, i, left);
                Permutations(nums, left + 1, right);
                swap(nums, i, left);
            }
        }
    }
    public static void swap(int [] arr, int s, int e){//////////
        int temp = arr[s];
        arr[s] = arr[e];
        arr[e] = temp;
    }
    public static void main(String []args){
//        println("gjgj");
        /////////
        String str = "gjrlwp";
        println(str);
        char[] chs = str.toCharArray();
        Arrays.sort(chs);
        println(String.valueOf(chs));
//        char ch = 'b';#######
//        int c = 98;
//        println((char)c);
//        byte b = 3;
//        int c = b + 2;

//        boolean flag = false;
//        while (!flag){
//            println("kkkkkk");
//            flag = true;
//        }
//        println((1 | 5 ) >>> 1);#
//        int k = 10;
//        if (k == 0){
//            println("kkkkkk");
//        }
//        if ((k&1) == 0){
//            println("pppppp");
//        }
//        String str = "asdfghjkkl";
//        println(str.substring(1,5));
//        int [][] a = {{0,1,2},{3,4,5},{6,7,8},{9,10,11}};
//        println(a.length);
//        println(a[0].length);
        int p = 2;
        println(Integer.toBinaryString(p));
//        p >>= 1;
//        println(Integer.toBinaryString(p));
//        println(p&2);
//        p >>= 1;
//        println(Integer.toBinaryString(p));
//        println(p&1);
//        p >>= 1;
//        println(Integer.toBinaryString(p));
//        println(p&1);
//        int [] arr = {1,2,3};
//        Permutations(arr, 0 , arr.length - 1);//这里减1

//        for (; p != 0; p >>= 1){
//            println(p);
//            println(p&1);
//        }
//        p>>=1;
//        println(p);
//        println(p&1);
//        StringBuffer [] str =new StringBuffer[100];
//        for (int i=0;i<str.length;i++)
//            str[i]=new StringBuffer();
//        str[1].append("11");
//        println(str[1].length());
//        println(str[1].charAt(1));

//        String str="()(())(";
//        int temp=str.length();
//        for (int j=str.length()-1;j>0;j--){
//            if (str.charAt(j)==')'){
//                println(j);
//                temp=j;
//                break;
//            }
//        }
//        String s=str.substring(0,temp+1);
//        print(s);
//        println((Integer.MAX_VALUE)/5000);
//        long l=-2147483648;
//        println(-l);
//        int [] a={5,4,3,2};
//        int len=a.length;
//        int []temp =a.clone();
//        Arrays.sort(temp,0,3);
//        int j;
//        for( j=len-1;j>=0;j--){
//            println("llll"+temp[j]);
//            if(temp[j]<a[0]){
//                println("llll"+temp[j]);
//                temp[j]=temp[j]+temp[j+1];
//                temp[j+1]=temp[j]-temp[j+1];
//                temp[j]=temp[j]-temp[j+1];
//            }
//            if (j==0){
//                temp[j]=temp[j]+temp[j+1];
//                temp[j+1]=temp[j]-temp[j+1];
//                temp[j]=temp[j]-temp[j+1];
//            }
////            temp[0]=a[0];
//        }
//        for (int i =0;i<a.length;i++)
//            print(temp[i]+" ");
//        println();
////        switch_ele(a[1],a[2]);
//        for (int i =0;i<a.length;i++)
//            print(a[i]+" ");
//        println();

//        StringBuffer []strbuffer=new StringBuffer[10];134432476
//        println(strbuffer.length);
//        String s="DCXXI";
//        s.su
//        if(s.indexOf("DC")!=-1)
//            print("lllllll");
//        print(s);
//        println();
//        s=s.replaceFirst("D","");
//        print(s);
//        println(Math.log(8)/Math.log(2));
//        int [] a={1,4,3,5,8,0,1,3,5,6,0};
//        Arrays.sort(a);
//        for(int i=0;i<a.length;i++)
//            print(a[i]);
//        sortID(a);
//        print(Integer.MIN_VALUE);
//        int [] a={3,5,4,2,1};
//        int []b=a.clone();
//        Arrays.sort(b);
//        for (int i=0;i<b.length;i++){
//            print(b[i]);
//        }
//        println();
//        for (int i=0;i<b.length;i++){
//            print(a[i]);
//        }



    }
//    public static class ListNode {
//        int val;
//        ListNode next;
//
//        public ListNode(int x) {
//            val = x;
//        }
//
//        public ListNode add(int x) {
//            ListNode tem = new ListNode(x);
//            this.next = tem;
//            return tem;
//        }
//    }
//    public static void test(ListNode l1){
//        while(l1!=null)
//        {
//            print(l1.val);
//            l1=l1.next;
//        }
//        if (l1==null)
//            println(false);
////        while(l1!=null)
////        {
////            print(l1.val);
////            l1=l1.next;
////
////        }
//    }
//    public static void main(String [] args){
//        ListNode l1=new ListNode(5);
////        println(l1.val);
//        ListNode p=l1;
//        l1= l1.add(4);
//        l1= l1.add(6);
//        l1=p;
////        while(l1!=null){
////            print(l1.val);
////            l1=l1.next;
////        }
//        test(l1);
//    }
}

