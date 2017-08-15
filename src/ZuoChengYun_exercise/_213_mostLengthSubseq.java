package ZuoChengYun_exercise;
/**
 * 目的：最长公共子串  必须连续
 * Created by XCY on 2017/8/9.
 * dp[i][j] 表示必须把str1[i] 与str2[j]当作公共子串最后一个字符的情况下，公共子串最长多长
 */
public class _213_mostLengthSubseq {

    public int[][] getdp(char[] str1, char[] str2){
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < str1.length; i++){
            dp[i][0] = str1[i] == str2[0] ? 1 : 0;
        }
        for (int j = 1; j < str2.length; j++){
            dp[0][j] = str2[j] == str1[0] ? 1 : 0;
        }
        for (int i = 1; i< str1.length; i++){
            for (int j = 1; j < str2.length; j++){
                dp[i][j] = str1[i] == str2[j] ? (dp[i - 1][j - 1] + 1) : 0;
                //两种方法都可以
//                if (str1[i] == str2[j]){
//                    dp[i][j] = dp[i - 1][j - 1] + 1
//                }
            }
        }
        return dp;
    }

    public String mostLengthSubseq(String str1, String str2){
        if (str1 == null || str2 == null || str1.equals("") | str2.equals("")){
            return "";
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getdp(chs1,chs2);
        int end = 0;//最后相等以为下标
        int max = 0;
        for (int i = 0; i < chs1.length; i++){
            for (int j = 0; j < chs2.length; j++){
                if (dp[i][j] > max){
                    end = i;//也可以等于j
                    max = dp[i][j];
                }
            }
        }
        return str1.substring(end - max + 1,end + 1);//含前不含后
    }

    //观察知道dp[i][j] 的变化只与dp[i-1][j-1]有关 因此有下面方法
    public String mostLengthSubseq2(String str1, String str2){
        if (str1 == null || str2 == null || str1.equals("") | str2.equals("")){
            return "";
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int end = 0;//最大长度更新时，记录子串结尾的位置
        int max = 0;//记录最大长度
        int row = 0;//斜线开始位置的行
        int col = chs2.length - 1;//斜线开始位置的列
        while (row < chs1.length){
            int i = row;
            int j = col;
            int len = 0;//用来更新以chs1[i](chs2[j])结尾的公共子串的长度
            //从(i,j)开始向右下方遍历
            while (i < chs1.length && j < chs2.length){
                if (chs1[i] != chs2[j]){
                    len = 0;
                }else {
                    len ++;
                }
                if (len > max){
                    max = len;
                    end = i;
                }
                i++;
                j++;
            }
            if (col > 0){//斜线开始位置的列先向左移动
                col--;
            }else {//列移动到最左之后，向下移动
                row++;
            }
        }
        return str1.substring(end - max + 1, end + 1);
    }

}
