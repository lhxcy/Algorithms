package ZuoChengYun_exercise;
/**
 * 目的：跳跃游戏
 * 给定arr，arr[i] = k 表示可以从i位置向右跳1~k个位置
 * 如果从位置0开始，返回最少跳几次能跳到arr最后的位置
 * Created by XCY on 2017/8/13.
 *
 */

public class _235_jumpGame {
    //jump代表目前跳了多少步，
    //cur代表只能跳jump步最远能到达的位置
    //next代表如果再跳一步，最远能到达的位置
    public static int jump(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int jump = 0;
        int next = 0;
        int cur = 0;
        for (int i = 0; i < arr.length; i++){
            if (cur < i){
                System.out.println(i);
                jump++;
                cur = next;
                System.out.println(cur+"     llll");
            }
            next = Math.max(next, i + arr[i]);// i + arr[i]表示从位置i开始跳arr[i]到哪个位置
        }
        return jump;
    }
    public static void main(String[] args){
        int[] arr = {3,2,3,1,1,4};
        System.out.println(jump(arr));
    }
}
