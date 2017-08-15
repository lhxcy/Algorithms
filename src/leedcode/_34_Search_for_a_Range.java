package leedcode;

/**
 * Created by XCY on 2017/5/29.
 * 先用二分法找到一个目标元素，记录位置
 * 然后左右查找与其相同的元素记录下标
 */
public class _34_Search_for_a_Range {
    public int[] searchRange(int[] nums, int target) {
        int []ans={-1,-1};
        int []ans1={0,0};
        if(nums.length==0)
            return ans;
        if(nums.length==1){
            if(nums[0]==target)
                return ans1;
            else return ans;
        }
        int start=0;
        int end=nums.length-1;
        int temp=-1;
        while(start<=end){
            int mid=(start+end)/2;
            if(nums[mid]==target){
                temp=mid;
                break;
            }
            if(nums[mid]>target){ end=mid-1; }
            if(nums[mid]<target){ start=mid+1; }
        }
        if(temp==-1){ return ans; }
        else{
            int last=temp+1; int first=temp-1;
            while(last<nums.length&&nums[last]==target){ ++last; }
            while(first>=0&&nums[first]==target){ --first;}
            ans[0]=first+1; ans[1]=last-1;
            return ans;
        }
    }
}
