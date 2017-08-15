package ZuoChengYun_exercise;
/**
 * 目的：换钱的最小货币数
 * Created by XCY on 2017/8/7.
 * 1: 货币可以重复使用
 * 2：货币不可以重复使用
 */
public class _191_minCoins {
    //arr为值为正且不重复的数组，如果arr长度为N，则生成行数为N，列数为aim+1的动态规划表dp
    //dp[i][j] 表示在可以任意使用arr[0..i]货币的情况下，组成j所需要的最小张数
    //dp[i][j] = min{dp[i-1][j-k*arr[i]]+k(0<=k)}  (1)=>
    //dp[i][j] = min{dp[i-1][j],dp[i-1][j-k*arr[i]]+k(1<=k)} (2) =>
    //dp[i][j] = min{dp[i-1][j],min(dp[i-1][j-arr[i]-k*arr[i]]+k(0<=k))}(3)
    //dp[i-1][j-k*arr[i]]+k(0<=k)
    //********************
    //又因为(1),把j-arr[i] 看作一个整体，则
    // min(dp[i-1][j-arr[i]-k*arr[i]]+k(0<=k)) = dp[i][j-arr[i]]
    // *****************************
    public int minCoins1(int [] arr, int aim){//没太理解
        if (arr == null || arr.length ==0 || aim < 0){
            return -1;
        }
        int n = arr.length;
        int max = Integer.MAX_VALUE;
        int [][] dp = new int [n][aim+1];
        //默认dp[0][0] = 0 因为不需要任何货币即可组成0元
        for (int j = 1; j <= aim; j++){
            dp[0][j] = max;
            if (j - arr[0] >= 0 && dp[0][j - arr[0]] != max){//初始化第一行
                dp[0][j] = dp[0][j - arr[j]] +1;
            }
        }
        int left = 0;
        for (int i = 1; i < n; i++){
            for (int j = 1; j <= aim; j++){
                left = max;
                if (j - arr[i] > 0 && dp[i][j - arr[i]] != max){
                    left = dp[i][j - arr[i]] +1;//使用当前货币的情况下的最小次数
                                                //关键是可以使用多次当前货币
                }
                dp[i][j] = Math.min(left,dp[i - 1][j]);
            }
        }
        return dp[n-1][aim] != max ? dp[n-1][aim] : -1;
    }

    public int minCoins2(int [] arr, int aim){
        if (arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        int n = arr.length;
        int max = Integer.MAX_VALUE;
        int [] dp = new int [aim+1];
        for (int j = 1; j <= aim; j++){
            dp[j] = max;
            if (j - arr[0] >= 0 && dp[j - arr[0]] != max){
                dp[j] = dp[j - arr[0]] + 1;
            }
        }
        int left = 0;
        for (int i = 1; i < n; i++){//一行一行更新
            for (int j = 1; j <= aim; j++){
                left = max;
                if (j - arr[i] >= 0 && dp[j - arr[i]] != max){
                    left = dp[j - arr[i]] + 1;
                }
                dp[j] = Math.min(dp[j],left);
            }
        }
        return dp[aim] != max ? dp[aim] : -1;
    }

    //补充问题，不允许使用重复货币
    //dp[i][j] 表示在可以任意使用arr[0..i]货币的情况下，组成j所需要的最小张数
    //dp[i][j] 可能由不使用arr[i]货币dp[i-1][j]组成或只是用一张arr[i]货币dp[i-1][j-arr[i]]+1

    public int minCoins3(int [] arr, int aim){
        if (arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        int n = arr.length;
        int max = Integer.MAX_VALUE;
        int [][] dp = new int [n][aim + 1];
        for (int j = 1; j <= aim; j++){
            dp[0][j] = max;
        }
        if (arr[0] <= aim){
            dp[0][arr[0]] = 1;
        }
        int left = 0;
        for (int i = 1; i < n; i++){
            for (int j = 1; j <= aim; j++){
                left = max;
                if (j - arr[i] >= 0 && dp[i - 1][j - arr[i]] != max){
                    left = dp[i - 1][j - arr[i]] + 1;
                }
                dp[i][j] = Math.min(left,dp[i - 1][j]);
            }
        }
        return dp[n - 1][aim] != max ? dp[n - 1][aim] : -1;
    }

    public int minCoins4(int [] arr, int aim){//压缩矩阵
        if (arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        int n = arr.length;
        int max = Integer.MAX_VALUE;
        int [] dp = new int[aim + 1];
        for (int j = 1; j <= aim; j++){
            dp[j] = max;
        }
        if (arr[0] <= aim){
            dp[arr[0]] = 1;
        }
        int left = 0;
        for (int i = 1; i < n; i++){
            for (int j = aim; j > 0; j--){//因为前面值不能改变所以倒着处理
                left = max;
                if (j - arr[i] >= 0 && dp[j - arr[i]] != max){
                    left = dp[j - arr[i]] + 1;
                }
                dp[j] = Math.min(left,dp[j]);//这里用到其那面的值
            }
        }
        return dp[aim] != max ? dp[aim] : -1;
    }

}
