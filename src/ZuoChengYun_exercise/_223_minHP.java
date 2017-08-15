package ZuoChengYun_exercise;

import java.util.Map;

/**
 * 目的：龙与地下城游戏问题
 * 给定一二维数组map，从左上到右下，只能向右向下，遇到正值加负值减，不能使值小于1
 * 求初始值最小为多少
 * Created by XCY on 2017/8/9.
 *
 */
public class _223_minHP {

    public static int minHP1(int[][] m){
        //dp[i][j]表示如果骑士要走上位置(i,j)，并且从该位置选一条最优路径，
        // 最后走到右下角，骑士起码应该剩余的血量
        //dp[i][j+1]表示如果骑士从当前位置向右走并最终到达右下角的血量要求
        //dp[i+1][j]表示如果骑士从当前位置向下走并最终到达右下角的血量要求
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0){
            return 1;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row--][col--];
        dp[row][col] = m[row][col] > 0 ? 1 : 1 - m[row][col];
        for (int j = col - 1; j >= 0; j--){
            dp[row][j] = Math.max(dp[row][j + 1] - m[row][j],1);
        }
        for (int i = row - 1; i >= 0; i--){
            dp[i][col] =Math.max(dp[i + 1][col] - m[i][col], 1);//这里如果不写这句话就需要加一个循环处理
            for (int j = col - 1; j >= 0; j--){
                dp[i][j] = Math.min(Math.max(dp[i][j + 1] - m[i][j], 1) , Math.max(dp[i + 1][j] - m[i][j], 1));
    //该句可以改为定义两个变量来接Math.max(dp[i][j + 1] - m[i][j], 1)与Math.max(dp[i + 1][j] - m[i][j], 1)，
    // 然后在比较，见minHP2
//                dp[i][j] = Math.min(dp[i][j + 1] - m[i][j],dp[i + 1][j] - m[i][j]);//两种方法
//                dp[i][j] = dp[i][j] >0 ? dp[i][j] : 1;
            }
        }
        return dp[0][0];

    }


    //压缩空间
    public static int minHP2(int[][] m){
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0){
            return 1;
        }
        int more = Math.max(m.length, m[0].length);
        int less = Math.min(m.length, m[0].length);
        boolean rowmore = more == m.length;
        int[] dp = new int[less];
        int tmp = m[m.length - 1][m[0].length - 1];
        dp[less - 1] = tmp > 0 ? 1 : 1 - tmp;
        int row = 0;
        int col = 0;
        for (int j = less - 2; j >= 0; j--){
            row = rowmore ? more - 1 : j;
            col = rowmore ? j : more - 1;
            dp[j] = Math.max(dp[j + 1] - m[row][col], 1);
        }
        int right = 0;
        int down = 0;
        for (int i = more - 2; i >= 0; i--){
            row = rowmore ? i : less - 1;
            col = rowmore ? less - 1 : i;
            dp[less - 1] = Math.max(1,dp[less - 1] - m[row][col]);//每次都需要先更新最后的位置
            for (int j = less - 2; j >= 0; j--){
                row = rowmore ? i : j;
                col = rowmore ? j : i;
                right = Math.max(1,dp[j + 1] - m[row][col]);
                down = Math.max(1,dp[j] - m[row][col]);
                dp[j] = Math.min(right,down);
            }
        }
        return dp[0];
    }



    public static void main(String[] args){
        int[][] m = {{-2,-3,3},{-5,-10,1},{0,30,-5}};
        System.out.println(minHP1(m));
        System.out.println(minHP2(m));
    }
}
