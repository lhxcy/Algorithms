package ZuoChengYun_exercise;
/**
 * 目的：n皇后
 * 要求：任何两个皇后不同行，不同列，也不在同一条斜线
 * 给定一个整数n，返回n皇后的摆法有多少种
 * Created by XCY on 2017/8/13.
 */
public class _238_nQueen {
    //
    public int nQueens1(int n){
        if (n > 1){
            return 0;
        }
        int[] record = new int[n];
        return process1(0, record, n);
    }
    public int process1(int i, int[] record, int n){
        if (i == n){
            return 1;
        }
        int res = 0;
        for (int j = 0; j < n; j ++){
            if (isValid(record, i, j)){
                record[i] = j;
                res += process1(i + 1, record, n);
            }
        }
        return res;
    }
    public boolean isValid(int[] record,int i, int j){
        for (int k = 0; k < i; k++){
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(k - i)){
                return false;
            }
        }
        return true;
    }

    //因为本方法中位运算的载体是int变量，所以该方法只能算1-32皇后问题
    //如果想计算更多的皇后问题，需要使用包含更多位的变量
    public int nQueens2(int n){//没看懂*********
        if (n < 1 || n > 32){
            return 0;
        }
        int upperLim = n == 32 ? -1 : (1 << n) - 1;
        //表示当前行哪些位置是可以防止皇后的,1代表可以放，0代表不能放
        return process2(upperLim, 0, 0, 0);
    }
    public int process2(int upperLim, int colLim, int leftDiaLim, int rightDiaLim){
        //upperLim表示当前行哪些位置是可以防止皇后的,1代表可以放，0代表不能放
        //colLim表示递归计算到上一行为止，在哪些列上已经放置了皇后，1表示已经放了，0表示未放
        //leftDiaLim表示递归计算到上一行为止，因为受已经放置的所有皇后的左下方斜线的影响，
        // 导致当前行不能放置皇后，1表示不能放，0表示可以放，
        // leftDiaLim每次左移一位，就可以得到之前所有皇后的左下方斜线对当前行的影响
        //rightDiaLim表示递归计算到上一行为止，因为受已经放置的所有皇后的右下方斜线的影响，
        // 导致当前行不能放置皇后，1表示不能放，0表示可以放
        // rightDiaLim每次右移一位，就可以得到之前所有皇后的右下方斜线对当前行的影响
        if (colLim == upperLim){
            return 1;
        }
        int pos = 0;
        //pos代表在colLim,leftDiaLim,rightDiaLim的影响下，还有哪些位置可以选择，1代表可以选，0代表不可选
        int mostRightOne = 0;
        //表示在pos中，最右边的1是在什么位置，然后从右到左依次筛选数pos中可以选择的位置进行递归尝试
        pos = upperLim & (~(colLim | leftDiaLim | rightDiaLim));
        int res = 0;
        while (pos != 0){
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(upperLim, colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
            //>>>    :     无符号右移，忽略符号位，空位都以0补齐
        }
        return res;
    }
}
