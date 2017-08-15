package ZuoChengYun_exercise;
/**
 * 目的：求矩阵的最小路径和
 *      从左上角开始每次只能向右或者向下走，最后到达右下角位置，返回路径和
 * Created by XCY on 2017/8/7.
 * 1: dp[i][j] = Math.min(dp[i-1][j]+m[i][j],dp[i][j-1]+m[i][j]);
 * 2: 如果给定矩阵列数小于行数，仍然可用这个方法，但是如果给定行数小于列数，
 *    那么就生成长度为行数的arr，然后令arr更新成dp矩阵的每一列的值，从左至右滚动过去
 */
public class _187_minPath {
    public static int minPathSum1(int[][] m){
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0){
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int [][] dp = new int [row][col];
        dp[0][0] = m[0][0];
        for (int i = 1; i < row; i++){
            dp[i][0] = dp[i-1][0] + m[i][0];
        }
        for (int j = 1; j < col; j++){
            dp[0][j] = dp[0][j-1] + m[0][j];
        }
        for (int i = 1; i < row; i++){
            for (int j = 1; j< col; j++){
                dp[i][j] = Math.min(dp[i-1][j]+m[i][j],dp[i][j-1]+m[i][j]);
            }
        }
        return dp[row-1][col-1];
    }

    public static int minPathSum2(int [][] m){
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0){
            return 0;
        }
        int more = Math.max(m.length, m[0].length);//行数与列数较大的那个为more
        int less = Math.min(m.length, m[0].length);
        boolean rowmore = more == m.length;//行是否为较大的
        int [] arr = new int [less];
        arr[0] = m[0][0];
        for (int i = 1; i < less; i++){
            arr[i] =arr[i-1] + (rowmore ? m[0][i] : m[i][0]);//第一次更新
        }
        for (int i = 1; i < more; i++){
            arr[0] = arr[0] + (rowmore ? m[i][0] : m[0][i]);//先更新arr[0],这是arr[0]就相当于dp[i][0]
            for (int j = 1; j < less; j++){
                arr[j] = Math.min(arr[j-1],arr[j]) + (rowmore ? m[i][j] : m[j][i]);//更新
            }
        }
        return arr[less-1];
    }

    public static void main(String [] args){
        int [][] m = {{1,3,5,9},{8,1,3,4},{5,0,6,1},{8,8,4,0}};
//        int res = minPathSum1(m);
        int res = minPathSum2(m);
        System.out.println(res);
    }
}
