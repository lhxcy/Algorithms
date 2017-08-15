package leedcode;

import java.util.Arrays;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/4/7.
 */
public class _3SumClosest {
    public static int threeSumClosest(int[] nums, int target) {
        int min=Integer.MAX_VALUE;//存放距离
        int result=Integer.MAX_VALUE;//存放三数之和
        int len=nums.length;
        Arrays.sort(nums);

        for (int i=0;i<len-1;i++){
            if (i==0||i>0&&nums[i]!=nums[i-1]) {
                int low = i + 1, high = len - 1, remainder = target - nums[i];
                //remainder保存的是目标值减去nums[i]的值，用来与另两个值之和比较

                while (low < high) {
                    int temp=nums[low] + nums[high] - remainder;
                    println("temp:"+temp+" low:"+low+" high:"+high+" i:"+i);
                    if (temp==0)
                        return target;
                    else if (temp<0) {
                            if (Math.abs(temp) < min){
                                min = Math.abs(temp);
                                result=nums[i]+nums[low]+nums[high];//保存三个数的和，因为min保存的是距离，所以不能返回min
                            }
                            low++;
                        }
                        else if (temp>0) {
                            if (Math.abs(temp) < min){
                                min = Math.abs(temp);
                                result=nums[i]+nums[low]+nums[high];
                            }
                            high--;
                    }

                }
                println("lalal:"+min);
            }


        }
        println("result:   "+result);

        return result;
    }
    public static void main(String[] args){
        int [] nums={-1,2,1,-4};
        int result=threeSumClosest(nums,1);
        println(result);

    }
}
