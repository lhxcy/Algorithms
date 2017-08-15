package ZuoChengYun_exercise;
/**
 * 目的：换钱方法数，任意货币可以使用任意张数,求一共多少种方法
 * Created by XCY on 2017/8/8.
 */
public class _196_coins {
    public int coins1(int [] arr, int aim){//暴力 O(aim^N)
        if (arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        return process1(arr, 0 ,aim);
    }
    public int process1(int [] arr, int index, int aim){
        int res = 0;
        if (index == arr.length){
            res = aim == 0 ? 1 : 0;//刚好够减，则返回1，否则返回0
        }else {
            for (int i = 0; arr[index] *i <= aim; i++){
                res += process1(arr,index+1,aim - arr[i] * i);
            }
        }
        return res;
    }

    //map[i][j]表示递归过程p[i][j]的返回值，等于0表示没有计算过，
    // 等于-1表示计算过但是结果为0，不等于0或-1，表示p[i][j]的计算值
    public int coins2(int [] arr, int aim){//O(aim^2 * N)
        if (arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        int [][] map = new int [arr.length + 1][aim + 1];////////
        return process2(arr,0,aim,map);
    }
    public int process2(int [] arr, int index, int aim,int [][] map){
        int res = 0;
        if (index == arr.length){
            res = aim == 0 ? 1 : 0;
        }else {
            int mapValue = 0;
            for (int i = 0; arr[index] * i <= aim; i++){
                mapValue = map[index + 1][aim - arr[index] * i];
                if (mapValue != 0){
                    res = mapValue == -1 ? 0 : mapValue;
                }else {
                    res += process2(arr,index + 1,aim - arr[index] * i, map);
                }
            }
        }
        map[index][aim] = res == 0 ? -1 : res;//else之后才算完，因此这时候存储
        return res;
    }

    //动态规划
    //dp[i][j] 表示在可以任意使用arr[0..i]货币的情况下，组成j有多少中方法
    //dp[..][0] = 1  dp[0][k * arr[0]] = 1
    //其他位置，不使用arr[i]（dp[i - 1][j]）,使用1次arr[i]（dp[i - 1][j-arr[i]]）,使用2次arr[i].....的和
    public int coins3(int [] arr, int aim){//O(aim^2 * N)
        if (arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        int [][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++){
            dp[i][0] = 1;//初始化第1列
        }
        for (int j = 1;arr[0] * j <= aim; j++){
            dp[0][arr[0] * j] = 1;//初始化第1行
        }
        int num = 0;
        for (int i = 1; i < arr.length; i++){
            for (int j = 1; j <= aim; j++){
                num = 0;
                for (int k = 0;j - k * arr[i] >= 0; k++){
                    num += dp[i - 1][j - k * arr[i]];//使用k张arr[i]货币的方法数
                }
                dp[i][j] = num;
            }
        }
        return dp[arr.length - 1][aim];
    }

    //dp[i][j] = dp[i-1][j-k*arr[i]](0<=k)  (1)=>
    //dp[i][j] = dp[i-1][j] + dp[i-1][j-k*arr[i]](1<=k) (2) =>
    //dp[i][j] = dp[i-1][j] + (dp[i-1][j-arr[i]-k*arr[i]](0<=k))}(3)
    //由(1)得 dp[i-1][j-arr[i]-k*arr[i]](0<=k) = dp[i][j-arr[i]]
    //所以dp[i][j] =  dp[i-1][j] + dp[i][j-arr[i]]

    public int coins4(int [] arr, int aim){//O(aim * N)
        if (arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        int n = arr.length;
        int [][] dp = new int [n][aim + 1];
        for (int i = 0; i < n; i ++){
            dp[i][0] = 1;//初始化第1列
        }
        for (int j = 1; j * arr[0] <= aim; j++){
            dp[0][j * arr[0]] = 1;
        }
        for (int i = 1; i < n; i++){
            for (int j = 1; j <= aim; j++){
//                if (j - arr[i] >= 0){
//                    dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
//                }else {
//                    dp[i][j] = dp[i - 1][j];
//                }
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
            }
        }
        return dp[n -1][aim];
    }

    //空间压缩
    public int coins5(int [] arr,int aim){
        if (arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        int [] dp = new int[aim + 1];
        for (int i = 0; i * arr[0] <= aim; i++){
            dp[i * arr[0]] = 1;
        }
        for (int i = 1; i < arr.length; i++){
            for (int j = 1; j <= aim; j++){
                dp[j] += j - arr[i] >= 0 ? dp[j - arr[i]] : 0;
            }
        }
        return dp[aim];
    }

}
