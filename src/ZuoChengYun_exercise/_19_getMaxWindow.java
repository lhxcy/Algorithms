package ZuoChengYun_exercise;
import java.util.LinkedList;
import java.util.List;

/**
 * 谜底：得到窗口大小为w中的最大值
 * Created by XCY on 2017/7/10.
 * 时间复杂度O(n)
 * 定义数组res存储下标
 *
 */
public class _19_getMaxWindow {
    public int [] getMaxWindow(int [] arr, int w){
        if (arr == null || w < 1|| arr.length < w){
            return null;
        }
        LinkedList<Integer> qmax = new LinkedList<Integer>();
        int [] res = new int[arr.length-w+1];
        int index = 0;
        for (int i = 0; i < arr.length; i++){
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]){
                qmax.pollLast();
            }
            qmax.addLast(i);
            if (qmax.peekFirst() == i-w){
                qmax.pollFirst();
            }
            if (i >= w-1){
                res[index++] = arr[qmax.peekFirst()];
            }

        }
        return res;
    }
}
