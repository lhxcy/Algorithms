package leedcode;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/5/29.
 * 方法一：直接在nums中查找
 * 方法二：二分查找
 */
public class _33_Search_in_Rotated_Sorted_Array {
    public static int search(int[] nums, int target) {
        //方法一
//        for(int i=0;i<nums.length;i++){
//            if(nums[i]==target)
//                return i;
//        }
//        return -1;
        //方法二
        if (nums.length==0)
            return -1;
        int start=0;
        int end=nums.length-1;
        while (start<=end){
            int mid=(end-start)/2+start;
            println(mid+"mmm");
            if (nums[mid]==target)
                return mid;
            if (nums[mid]<nums[end]){
                if (target>nums[mid]&&target<=nums[end]) start=mid+1;
                else end=mid-1;
            }
            else {
                if (target<nums[mid]&&target>=nums[start]) end=mid-1;
                else start=mid+1;
            }
        }
        return -1;
    }
    public static void main(String []args){
        int [] nums={4,5,6,7,1,2,3};
//        int []nums1={5,7,7,8,8,10};
        int target=7;
        int ans=search(nums,target);
        println(ans);
    }
}
