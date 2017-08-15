package leedcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static algorithms.Print.print;
import static algorithms.Print.println;

/**
 * Created by XCY on 2017/4/11.
 */
public class _4Sum {
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list=new LinkedList();
        Arrays.sort(nums);
        int len=nums.length;
//        for(int i=0;i<len;i++)
//            print(nums[i]);
        if (len<4)
            return list;
        for (int j=0;j<len-3;j++) {
            for (int i = j+1; i < len - 2; i++) {
//                if ( i == 1 || i > 0 && nums[i] != nums[i - 1]) {
                    int low = i + 1, high = len - 1, remainder = target - nums[i]-nums[j];
//                    println("remainder:"+remainder);
                    while (low  < high) {
                        if (nums[low]  + nums[high] == remainder) {
                            List temp=(Arrays.asList(nums[j], nums[i], nums[low ], nums[high]));
                            if(!list.contains(temp))
                                list.add(temp);
//                            println("i:"+i+" j:"+j);
//                            print(list);
                            while (low < high && (nums[low] == nums[low + 1]))
                                low++;
                            while (low < high && nums[high] == nums[high - 1])
                                high--;
                            low++;
                            high--;
//                            println("low:"+low+" j:"+j);
//                            println(list);
                        } else if (nums[low] + nums[high] < remainder) low++;
                        else high--;
                    }
//                }
            }
        }
        return  list;

    }
    public static void main(String []args){
        int []nums={1,0,-1,0,-2,2};
        int []nums1={-3,-2,-1,0,0,1,2,3};
        List<List<Integer>> list=new LinkedList();
        list=fourSum(nums1,0);
        print(list);

    }
}
