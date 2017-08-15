package leedcode;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/5/9.
 */
public class _26_Remove_Duplicates_from_Sorted_Array {
    public static int removeDuplicates(int[] nums) {

        int len=nums.length;
        if(len==0)
            return 0;
        int count=1;
        for(int i=1;i<len;i++){

            if(nums[i]!=nums[i-1]){
                // count++;
                nums[count++]=nums[i];
            }


        }
        return count;
    }
    public static void main(String args[]){
        int [] nums={1,2,3,3,4};
        int t=removeDuplicates(nums);
        println(t);
    }
}
