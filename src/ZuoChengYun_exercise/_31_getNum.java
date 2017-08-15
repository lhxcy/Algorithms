package ZuoChengYun_exercise;
import java.util.LinkedList;

/**
 * 目的：求数组arr所有满足max(arr[i...j])-min(arr[i...j]) <= num 的 子数组个数
 * Created by XCY on 2017/7/11.
 * 若arr[i...j]满足情况，则它的每个子数组都满足arr[i...j-1]，arr[i...j-2]......arr[i...i]
 * i为前基准
 */
public class _31_getNum {
    public int getNum(int[] arr, int num){
        if (arr == null || arr.length == 0){
            return 0;
        }
        LinkedList<Integer> qmin = new LinkedList<Integer>();//存储窗口内最小值
        LinkedList<Integer> qmax = new LinkedList<Integer>();//存储窗口内最大值
        int i = 0;//前
        int j = 0;//后
        int res = 0;//记录满足的个数
        while (i < arr.length){
            while (j < arr.length){
                while (!qmin.isEmpty() && arr[j] <= arr[qmin.peekLast()]){
                    qmin.pollLast();
                }
                qmin.addLast(j);
                while (!qmax.isEmpty() && arr[j] >= arr[qmax.peekLast()]){
                    qmax.pollLast();
                }
                qmax.addLast(j);
                if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num){
                    break;//不满足情况，跳出
                }
                j++;
            }
            if (qmin.peekFirst() == i){
                qmin.pollFirst();//i为前已经计算过，故需要删除
            }
            if (qmax.peekFirst() == i){
                qmax.pollFirst();
            }
            res+=j-i;//计算总数
            i++;
        }
        return res;
    }

}
