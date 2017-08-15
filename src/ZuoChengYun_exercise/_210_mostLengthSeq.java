package ZuoChengYun_exercise;

/**
 * 目的：最长公共子序列   可以不连续
 * Created by XCY on 2017/8/8.
 */
public class _210_mostLengthSeq {

    public int[][] getdp(char[] str1, char[] str2)
    {
        //dp[i][j] 表示str1[0..i] 与str2[0..j]的最长公共子序列
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < str1.length; i++)
        {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }
        for (int j = 1; j < str2.length; j++)
        {
            dp[0][j] = Math.max(dp[0][j - 1], str2[j] == str1[0] ? 1 : 0);
        }
        for (int i = 1; i < str1.length; i++)
        {
            for (int j = 1; j < str2.length; j++)
            {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j])
                {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp;
    }

    //有了dp之后，然后求出公共子序列只需从右下角往左，上，左上移动与当前dp值比较就行
    public String mostLengthSeq(String str1,String str2){
        if (str1 == null || str2 == null || str1.equals("") || str2.equals(""))
        {
            return "";
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getdp(chs1, chs2);
        int m = chs1.length - 1;
        int n = chs2.length - 1;
        char[] res = new char[dp[m][n]];
        int index = res.length - 1;
        while (index >= 0)
        {
            if (n > 0 && dp[m][n] == dp[m][n - 1])
            {
                n--;
            }else if(m > 0 && dp[m][n] == dp[m - 1][n])
                {
                    m--;
                }else
                {
                    res[index--] = chs1[m];
                    m--;
                    n--;
                }
        }
        return String.valueOf(res);
    }

}
