package leedcode;

import java.util.Arrays;

import static algorithms.Print.print;
import static algorithms.Print.println;

/**
 * Created by XCY on 2017/5/25.
 * 思想：从后向前遍历，知道遇到第i个元素比第i-1个元素大，停止，
 * 如果i！=0，将其后面的元素排序，找出第一个大于它的前一个元素交换，记录其位置tempindex，然后排序tempindex到最后的数据
 * 如果i==0，说明是降序排列的，只需转为升序就好
 */
public class _31_Next_Permutation {
    public static void nextPermutation(int[] nums) {//
        int len=nums.length;
        int m=0;
        for( m=0;m<len-1;m++){
            if(nums[m]!=nums[m+1])
                break;
        }
        if (len>1&&m<len-1){
            int i;
            for(i =len-1;i>0;i--){
                if(nums[i]>nums[i-1])
                    break;
            }
            println(i);
            int tempindex = i;
//            if (i==len-1){
//                nums[i]=nums[i]+nums[i-1];
//                nums[i-1]=nums[i]-nums[i-1];
//                nums[i]=nums[i]-nums[i-1];
//            }
            if (i!=0){//&&i<len-1
                Arrays.sort(nums,i,len);

                for (int t=i;i<len;t++){
                    if (nums[t]>nums[i-1]){
                        nums[t]=nums[t]+nums[i-1];
                        nums[i-1]=nums[t]-nums[i-1];
                        nums[t]=nums[t]-nums[i-1];
                        tempindex=t;
                        break;
                    }
                }
            }
            if (i==0){
                Arrays.sort(nums,i,len);

                for (int t=i;i<len;t++){
                    if (nums[t]>nums[0]){
                            nums[t]=nums[t]+nums[0];
                            nums[0]=nums[t]-nums[0];
                            nums[t]=nums[t]-nums[0];
//                            tempindex=t;
                            break;
                        }
                }
            }
            if (i==0)
                Arrays.sort(nums,0,len);
            if (i==1){
                println("jfkkk");
                Arrays.sort(nums,1,len);
                println("pppppp");
            }
            if (i>1&&i<=len-1)
                Arrays.sort(nums,tempindex,len);

            }

        }

//    }
    public static void main(String []args){
//        int []nums={5,4,7,5,3,2};
        int [] nums={1,1};
        nextPermutation(nums);
        for(int k=0;k<nums.length;k++)
            print(nums[k]+" ");
    }
}
