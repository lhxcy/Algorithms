package ZuoChengYun_exercise;

/**
 * 字符串中数字子串的求和
 * str = "a1cd2e33" return 36
 * Created by XCY on 2017/8/16.
 * 1：忽略小数点字符
 * 2：如果数字子串左侧出现‘-’，当出现奇数次，则数字视为负数，偶数次为正
 *     a-1bc--12        -1,  12
 */
public class _243_strNumSum {
    public int strNumSum(String str){
        if (str == null || str.length() == 0){
            return 0;
        }
        char[] chs = str.toCharArray();
        int res = 0;//累加结果
        int num = 0;//当前收集到的数字
        boolean flag = true;//表示如果把num累加到res里，num是正还是负
        int cur = 0;
        for (int i = 0; i < chs.length; i++){
            cur = chs[i] - '0';
            if (cur < 0 || cur > 9){
                res += num;
                num = 0;
                if (chs[i] == '-'){
                    if (i - 1 > -1 && chs[i - 1] == '-'){
                        flag = !flag;
                    }else {
                        flag = false;
                    }
                }else {
                    flag = true;
                }
            }else {
                num = num * 10 + (flag ? cur : -cur);
            }
        }
        res += num;
        return res;
    }
}
