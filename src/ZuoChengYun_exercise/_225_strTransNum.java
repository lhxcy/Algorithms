package ZuoChengYun_exercise;
/**
 * 目的数字字符串转换为字母组合的种数
 * str全由数组组成 如果str中1个或2个相邻的两个字符组成的子串值在1-26，则这个子串可以转换为一个字母
 * 规定 1->A,2->B.....26->Z
 * Created by XCY on 2017/8/10.
 */
public class _225_strTransNum {

    public static int strTransNum1(String str){
        if (str == null || str.equals("")){
            return 0;
        }
        char[] chs = str.toCharArray();
        return process(chs,0);
    }
    //p(i)表示str[0..i-1]已经转换完毕，而str[i..n-1]还没有转换的情况下，最终合法的转换有多少种
    public static int process(char[] chs, int index){
        if (index == chs.length){
            return 1;
        }
        if (chs[index] == '0'){
            return 0;
        }
        int res = process(chs, index + 1);
        if (index + 1  < chs.length && (chs[index] - '0') * 10 +(chs[index + 1] - '0') < 27){
            res += process(chs, index + 2);
        }
        return res;
    }

    ///因为p(i)只与p(i+1),p(i+2)有关系
    public static int strTransNum2(String str){
        if (str == null || str.equals("")){
            return 0;
        }
        char[] chs = str.toCharArray();
        int cur = chs[chs.length - 1] == '0' ? 0 :1;
        int next = 1;
        int tmp = 0;
        for (int i = chs.length - 2; i >= 0; i--){
            if (chs[i] == '0'){
                next = cur;
                cur = 0;
            }else {
                tmp = cur;
                if ((chs[i] - '0') * 10 +(chs[i + 1] - '0') < 27){
                    cur += next;
                }
                next = tmp;
            }
        }
        return cur;
    }

    public static void main(String[] args){
        String str = "011";
        System.out.println(strTransNum1(str));
        System.out.println(strTransNum2(str));
    }
}
