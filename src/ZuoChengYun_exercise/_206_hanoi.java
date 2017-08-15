package ZuoChengYun_exercise;
/**
 * 目的：汉诺塔问题  打印最优路径
 * Created by XCY on 2017/8/8.
 *  1：圆盘1..i-1从from移到mid
 *  2：圆盘i从from移到to
 *  3：圆盘1..i-1从mid移到to
 */
public class _206_hanoi {

    public void hanoi(int n){
        if (n > 0){
            func(n, 1, 2, 3);
        }
    }

    public void func(int n, int from, int mid, int to){
        if (n == 1){
            System.out.println("move from " + from + " to " + to);
        }else {
            func(n - 1, from, to, mid);
            func(1, from, mid, to);
            func(n - 1, mid, from, to);
        }
    }

    //进阶问题
    //arr只含有1，2，3代表所有圆盘的目前状态，1左2中3由
    //arr[i] 代表第i+1个圆盘的位置
    //判断arr出现的状态是否是最优移动轨迹过程中出现的状态，若是返回是第几个状态，不是返回-1
    public int step1(int [] arr){
        if (arr == null || arr.length == 0){
            return -1;
        }
        return process(arr, arr.length, 1, 2, 3);
    }

    //s(i) = s(i-1)+1+s(i-1)  => s(i)+1 = 2(s(i-1)+1) => s(i) = 2^i - 1  s(i)为移动步数
    public int process(int [] arr, int i, int from, int mid, int to){//不太清楚
        if (i == -1){
            return 0;//初始状态
        }
        if (arr[i] != from && arr[i] != to){//当第i个圆盘在中柱上时，该情况不是最优情况的步骤
            return -1;
        }
        if (arr[i] == from){//此时说明圆盘i在左柱上，需要看0..i-1盘的情况
            return process(arr, i - 1, from, to, mid);//i-1个盘从左向中移动的步数
        }else {//arr[i] == to   此时说明圆盘i在右柱上，最起码走了2^(N-1)步，即前i-1个盘肯定走过
            int rest = process(arr, i -1, mid, from, to);//i-1个盘从中向右移动的步数
            if (rest == -1){
                return -1;
            }
            return (1 << i) + rest;//总移动步数
        }
    }

    public int step2(int [] arr){//改写为非递归
        if (arr == null || arr.length == 0){
            return -1;
        }
        int from = 1;
        int mid = 2;
        int to = 3;
        int i = arr.length - 1;
        int res = 0;
        int tmp = 0;
        while (i >= 0){
            if (arr[i] != from && arr[i] != to){
                return -1;
            }
            if (arr[i] == to){
                res += 1 << i;
                tmp = from;
                from = mid;
            }else {
                tmp = to;
                to = mid;
            }
            mid = tmp;
        }
        return res;
    }
}
