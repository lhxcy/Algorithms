package ZuoChengYun_exercise;
/**
 * 目的：最小编辑代价
 * 给定两个字符串str1，str2，再给定ic，dc，rc分别代表插入，删除和替换一个字符的代价，
 * 返回将str1编辑成str2的最小代价
 * Created by XCY on 2017/8/9.
 *
 * dp[i][j] 表示必须把str1[0..i-1] 编辑成str2[0..j-1]的最小代价
 * */
public class _217_minCost {

    public int minCost1(String str1, String str2, int ic, int dc, int rc){
        if (str1 == null || str2 == null){
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2  = str2.toCharArray();
        int row = chs1.length + 1;
        int col = chs2.length + 1;
        int dp[][] = new int[row][col];
        for (int i = 1; i < row; i++){
            dp[i][0] = i * dc;
        }
        for (int j = 1; j < col; j++){
            dp[0][j] = j * ic;
        }
        for (int i = 1; i < row; i++){
            for (int j = 1; j < col; j++){
                if (chs1[i] == chs2[j]){
                    dp[i][j] = dp[i - 1][j - 1];//此时需要将str1[0..i-2]编辑成str2[0,,j-2]
                }else {
                    dp[i][j] = rc + dp[i - 1][j - 1];
                }
                dp[i][j] = Math.min(dp[i][j],dp[i - 1][j] + dc);
                //先删后修改  删除str1[i-1],然后编辑str1[0..i-2]为str2[0..j-1]
                dp[i][j] = Math.min(dp[i][j],dp[i][j - 1] + ic);
                //先修改后插入  编辑str1[0..i-1]为str2[0..j-2],然后插入str2[j-1]
            }
        }
        return dp[row - 1][col - 1];
    }

    //空间压缩
    //上面的方法是把str2作为列对应的字符串，
    // 如果str1作为了列对应的字符串，把插入代价和删除代价对换一下即可

    //感觉这样不太好理解，可不可以不管长短直接将str2最为列对应的字符串，
    // 我个人认为可以 不过空间复杂度成了O(N),而不是O(min(M,N)),不过一般生成长度为min(M,N)的数组

    // dp[i][j]依赖于dp[i-1][j],dp[i][j-1],dp[i-1][j-1]所以需要一个变量保存dp[i-1][j-1],
    // 也就是dp[j-1] 没有更新前的数据
    public int minCost2(String str1, String str2, int ic, int dc, int rc){
        if (str1 == null || str2 == null){
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
        char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
        if (chs1.length < chs2.length){//若str1小则交换ic,dc
            int tmp = ic;
            ic = dc;
            dc = tmp;
        }
        int[] dp = new int[shorts.length + 1];
        for (int i = 1; i <= shorts.length; i++){
            dp[i] = i * ic;
        }
        for (int i = 1; i <= longs.length; i++){
            int pre = dp[0];//pre表示左上角的值,相当于dp[i-1][j-1]
            dp[0] = dc * i;//相当于初始化dp[i][0]
            for (int j = 1; j <= shorts.length; j ++){
                int tmp = dp[j];//dp[j]没更新之前先保存下来,相当于dp[i-1][j]
                //因为下面要改变dp[j]，所以才需要记忆dp[j]
                if (shorts[i - 1] == longs[j - 1]){
                    dp[j] = pre;/////
                }else {
                    dp[j] = rc + pre;//////
                }
                dp[j] = Math.min(dp[j], tmp + dc);
                dp[j] = Math.min(dp[j], dp[j - 1] + ic);
                pre = tmp;
            }
        }
        return dp[shorts.length];
    }
}
