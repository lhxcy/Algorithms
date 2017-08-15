package ZuoChengYun_exercise;
import java.util.HashMap;

/**
 * 目的：数组中的最长连续序列
 * 给定无序数组aee，返回最长的的连续序列的长度
 * Created by XCY on 2017/8/13.
 */
public class _236_longestConsecutive {
    //HashMap<Integer,Integer> map key代表遍历过某个数，value代表key这个数所在的最长连续序列的长度
    public int longestConsecutive(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int max = 1;
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        for (int i = 0; i < arr.length; i++){
            if (!map.containsKey(arr[i])){
                map.put(arr[i],1);
                if (map.containsKey(arr[i] - 1)){
                    max = Math.max(max, merge(map,arr[i] - 1, arr[i]));
                }
                if (map.containsKey(arr[i] + 1)){
                    max = Math.max(max, merge(map,arr[i], arr[i] + 1));
                }
            }
        }
        return max;
    }

    public int merge(HashMap<Integer,Integer> map, int less, int more){
        int left = less - map.get(less) + 1;
        int right =  more + map.get(more) - 1;
        int len = right - left + 1;
        map.put(left, len);
        map.put(right, len);
        return len;
    }
}
