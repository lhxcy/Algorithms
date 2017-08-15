package leedcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by XCY on 2017/7/10.
 */
public class _40_Combination2_Sum {
    private void solve(List<List<Integer>> res, int currentIndex, int count,
                       List<Integer> temp, int [] candidates, int target){
        if (count >= target){//当数字总和大于target break
            if (count == target){
                if (!res.contains(temp)){//如果res不含有temp则加入，进而控制不让每个元素重复出现
                    res.add(new LinkedList<Integer>(temp));
                }//等于时加入res
            }
            return;
        }
        for (int i = currentIndex; i < candidates.length; i++){//从当前下标开始遍历
            if (count + candidates[i] > target){//大于跳出循环
                break;
            }
            temp.add(candidates[i]);//候选元素加入temp
            solve(res, i+1, count + candidates[i], temp, candidates, target);//在候选元素加入temp基础上处理后续元素查看有没有合适组合
            temp.remove(temp.size()-1);
            //若temp加入该元素后不符合情况，则从temp中移除该元素，如若符合情况则该组合已经加入res，也许移除最后一个元素寻找移除该元素后的其他组合
        }

    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        List<Integer> temp = new LinkedList<Integer>();
        Arrays.sort(candidates);

        solve(res, 0, 0, temp, candidates, target);

        return res;



    }
}
