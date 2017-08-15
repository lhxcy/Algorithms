package leedcode;

/**
 * Created by XCY on 2017/5/9.
 */
public class _27_Remove_Element {
    public int removeElement(int[] nums, int val) {
        int len=nums.length;
        if(len==0)
            return 0;
        int count=0;
        for(int i = 0; i < len; i++){
            if(nums[i]!=val)
                nums[count++]=nums[i];
        }
        return count;

    }
}
