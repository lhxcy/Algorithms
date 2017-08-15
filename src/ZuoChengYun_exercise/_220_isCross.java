package ZuoChengYun_exercise;

/**
 * 目的：字符串的交错组成
 * str1,str2,aim,若aim包含且仅包含str1和str2，而且aim中属于str1的字符之间保持原来的顺序
 * 属于str2的保持在str2中的顺序，则称aim是str1与str2交错组成的
 * 判断是否交错组成
 * Created by XCY on 2017/8/9.
 * dp[i][j] 表示aim[0...i+j-1]能否被str1[0..i-1]和str2[0..j-1]交错组成
 * dp[i][0]表示aim[0..i-1]能否只被str1[0..i-1]交错表示
 * dp[0][j]表示aim[0..j-1]能否只被str2[0..j-1]交错表示
 */
public class _220_isCross {
    public boolean isCross1(String str1, String str2, String aim){
        if (str1 == null || str2 == null || aim == null){
            return false;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] chaim = aim.toCharArray();
        if (chaim.length != chs1.length + chs2.length){
            return false;
        }
        boolean[][] dp = new boolean[chs1.length + 1][chs2.length + 1];
        dp[0][0] = true;
        for (int i = 1; i <= chs1.length; i++){
            if (chs1[i - 1] != chaim[i - 1]){
                break;
            }
            dp[i][0] = true;
        }
        for (int j = 1; j <= chs2.length; j++){
            if (chs2[j - 1] != chaim[j - 1]){
                break;
            }
            dp[0][j] = true;
        }

//两种情况，如果dp[i-1][j]==true,则说明chaim[0..i+j-2]可以被chs1[0..i-2]和chs2[0..j-1]交错组成，
//再加上chs1[i] == chaim[i + j - 1]可以说明chaim[0..i+j-1]可以有chs1[0..i-1]和chs2[0..j-1]交错组成

//如果dp[i][j-1]==true,则说明chaim[0..i+j-2]可以被chs1[0..i-1]和chs2[0..j-2]交错组成
//再加上chs2[j] == chaim[i + j - 1]可以说明chaim[0..i+j-1]可以有chs1[0..i-1]和chs2[0..j-1]交错组成
        for (int i = 1; i <= chs1.length; i++){
            for (int j = 1; j <= chs2.length; j++){
                if ((dp[i - 1][j] && chs1[i] == chaim[i + j - 1]) || (dp[i][j - 1] && chs2[j] == chaim[ + j - 1])){
                    dp[i][j] = true;
                }
            }
        }
        return dp[chs1.length][chs2.length];
    }

    //压缩存储
    public boolean isCross2(String str1,String str2, String aim){
        if (str1 == null || str2 == null || aim == null){
            return false;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] chaim = aim.toCharArray();
        if (chaim.length != chs1.length + chs2.length){
            return false;
        }
        char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
        char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
        boolean[] dp = new boolean[shorts.length + 1];
        dp[0] = true;
        for (int i = 1; i <= shorts.length; i++){
            if (shorts[i - 1] != chaim[i - 1]){
                break;
            }
            dp[i] = true;
        }
        for (int i = 1; i <= longs.length; i++){
            dp[0] = dp[0] && longs[i - 1] == chaim[i - 1];
            for (int j = 1; j <= shorts.length; j++){
                if ((dp[j] && longs[i - 1] == chaim[i + j - 1]) || (dp[j - 1] && shorts[j - 1] == chaim[i + j - 1])){
                    dp[j] = true;
                }
            }
        }
        return dp[shorts.length];
    }

}
