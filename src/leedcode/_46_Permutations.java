package leedcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by XCY on 2017/8/8.
 */
public class _46_Permutations {
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        Permutations(nums, 0 ,nums.length - 1, res);
        System.out.println(res);
        return res;
    }

    public static void Permutations(int [] nums, int left, int right, List<List<Integer>> res){
//        int right = nums.length - 1;  //这时递归时可以不用传递right值，进而速度加快
        if (left == right){
            List<Integer> list = new LinkedList<Integer>();
            for (int i = 0; i < nums.length; i++){
                System.out.print(nums[i] + " ");
                list.add(nums[i]);
            }
            System.out.println(list);
            res.add(list);
            System.out.println();
        }else {
            for (int i = left; i <= right; i++){//因为传进来参数为长度减1，所以此时需要等号
                swap(nums, i, left);
                Permutations(nums, left + 1, right, res);
                swap(nums, i, left);
            }
        }
    }
    public static void swap(int [] arr, int s, int e){
        int temp = arr[s];
        arr[s] = arr[e];
        arr[e] = temp;
    }

    public static void main(String [] args){
        int [] nums = {1,2,3};
        permute(nums);

    }
}
