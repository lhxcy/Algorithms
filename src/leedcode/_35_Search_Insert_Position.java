package leedcode;

/**
 * Created by XCY on 2017/5/29.
 * 二分思想
 */
public class _35_Search_Insert_Position {
    public int searchInsert(int[] nums, int target) {
        if(nums.length==0)
            return 0;
        if(nums.length==1){
            if(nums[0]==target||nums[0]>target) return 0;
            else return 1;
        }
        int start =0;
        int end=nums.length-1;
        int ans=-1;
        while(start<=end){
            int mid=(start+end)/2;
            if(nums[mid]==target)   {ans= mid; break;}
            if(nums[mid]>target) end=mid-1;
            if(nums[mid]<target) start=mid+1;
        }
        if(ans==-1)
            return start;
        else return ans;

    }
}
