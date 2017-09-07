package leedcode;

import java.util.*;

import static algorithms.Print.print;
import static algorithms.Print.println;
/*
/**
 * Created by XCY on 2017/4/4.
 */
public class _3Sum {//////////////////////////
    /**************
    //复杂度过高
     **************/
    /*public static int BinnarySearch(int []a,int low,int high,int key){
        while(low<=high){
            int mid=(low+high)/2;
            if(key>a[mid]) low=mid+1;
            else if(key<a[mid]) high=mid-1;
            else return mid;
        }
        return -1;
    }
    public static List<List<Integer>> threeSum(int[] nums) {

        List<Integer> list1=new ArrayList<Integer>();
        List<List<Integer>> list=new ArrayList<List<Integer>>();
        int len=nums.length;
        int indexOfFirstNoZero=0;
        if(len<3)
            return list;
        Arrays.sort(nums);
        for(int i=0;i<len;i++){
            if (nums[i]>=0){
                indexOfFirstNoZero=i;
                break;
            }
        }
        if (len>indexOfFirstNoZero+2&&nums[indexOfFirstNoZero]==0&&nums[indexOfFirstNoZero+1]==0&&nums[indexOfFirstNoZero+2]==0){
            list1.add(0);
            list1.add(0);
            list1.add(0);
            list.add(list1);
            list1=new ArrayList<Integer>();
        }
        int i_max=0;//存储i的最大值
        if(indexOfFirstNoZero>0)
            i_max=indexOfFirstNoZero;
        else i_max=len;
        for(int i=0;i<i_max;i++){
            for(int j=i+1;j<len-1;j++){
                int temp=0;
//                println("lllllll");
                int k=0-(nums[i]+nums[j]);
                if (j<indexOfFirstNoZero)
                    temp=BinnarySearch(nums,indexOfFirstNoZero,len-1,k);

                else  if (j>=indexOfFirstNoZero)
                    temp=BinnarySearch(nums,j+1,len-1,k);

                println("kkkkkk");
                println(temp);
                if(temp==-1)
                    continue;
                else{
                    list1.add(nums[i]);
                    list1.add(nums[j]);
                    list1.add(nums[temp]);

                }
                if(!list1.isEmpty()){

//                    println(list1);

                    if(!list.contains(list1)){
//                        println("lllll");
                        list.add(list1);
//                        println(list);

//                    println("mmmmmm");
                        list1=new ArrayList<Integer>();
                    }
                    else
                        list1=new ArrayList<Integer>();
                }
            }

        }

//        println(list);
        return list;
    }*/
    public static List<List<Integer>> threeSum(int[] nums) {

/*****************
//参考，自己的算法时间复杂度太高
********************/
//        List<Integer> list1=new LinkedList<>();
        List<List<Integer>> list=new LinkedList<>();
        int len=nums.length;
        int indexOfFirstNoZero=0;
        if(len<3)
            return list;
        Arrays.sort(nums);
        for (int i=0;i<len-2;i++){
            if (i==0||(i>0&&nums[i]!=nums[i-1])){
                int low=i+1, high=len-1,remainder=0-nums[i];
                while (low<high){
                    if (nums[low]+nums[high]==remainder){
                        list.add(Arrays.asList(nums[i],nums[low],nums[high]));
                        while (low<high&&nums[low]==nums[low+1])low++;
                        while (low<high&&nums[high]==nums[high-1])high--;
                        low++;high--;
                    }
                    else if (nums[low]+nums[high]<remainder)
                        low++;
                    else high--;
                }
            }
        }
        return list;
    }
    public static void  main(String []args){
        int [] nums={-1,0,1,2,-1,-4};
        int []nums2={-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0};
        int [] nums1={82597,-9243,62390};
        List<List<Integer>> list=new ArrayList<List<Integer>>();

        list=threeSum(nums2);
        println(list);

    }
}
