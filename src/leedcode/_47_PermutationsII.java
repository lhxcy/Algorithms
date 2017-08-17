package leedcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 定义used数组来确定该元素有没有被使用过
 *
 * Created by XCY on 2017/8/13.
 */
public class _47_PermutationsII {
    public static List<List<Integer>> permuteUnique(int[] nums){//看别人方法
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0){
            return res;
        }
        boolean[] used = new boolean[nums.length];
        List<Integer> list = new ArrayList<Integer>();
        Arrays.sort(nums);
        //Sort the array "int[] nums" to make sure we can skip the same value.
        PermutationsII(nums, used, list, res);
        return res;
    }
    public static void PermutationsII(int[] nums, boolean[] used, List<Integer> list, List<List<Integer>> res){
        if (list.size() == nums.length){
            res.add(new ArrayList<>(list));
        }else {
            for (int i = 0; i < nums.length; i++){
                if (used[i]){
                    continue;//第i个元素已经使用过，所以跳过后面操作
                }
                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]){
                    //这里判断条件也可以是i > 0 && nums[i] == nums[i - 1] && used[i - 1]，
                    // 此时重复时相当于只是使用前面一个相同而且该元素未使用的情况，
                    // 即当used[i - 1]用过并且也满足前两个条件时，跳过该元素

                    //其实两者结果一样，速度也差不多

                    //i > 0 && nums[i] == nums[i - 1] && !used[i - 1]
                    //也即当used[i - 1]没有用过并且也满足前两个条件时，跳过该元素
                    //when a number has the same value with its previous,
                    // we can use this number only if his previous is used
//                    System.out.println("kkkkkkk ");
                    continue;
                }
                used[i] = true;
                list.add(nums[i]);
                PermutationsII(nums, used, list, res);
                list.remove(list.size() - 1);
                used[i] = false;
            }
        }
    }
    public static void main(String[] args){
        int[] nums = {1,1,1,2};
        System.out.println(permuteUnique(nums));
    }
}
