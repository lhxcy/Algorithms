package ZuoChengYun_exercise;
/**
 * 目的：表达式得到期望结果的组成种数
 * 给定一个只有0，1，&，|,^五种字符组成的字符串express和布尔变量desired，
 * 返回express能有多少种组合方式，可以达到desired的结果
 *
 * Created by XCY on 2017/8/10.
 *
 * 表达式长度一定是奇数
 * 表达式奇数位一定是数字，偶数位一定是&，|，^之一
 */
public class _228_expressNum {

    public boolean isValid(char[] exp){//判断表达式是否合法
        //表达式长度一定是奇数
        //表达式奇数位一定是数字，偶数位一定是&，|，^之一
        if ((exp.length & 1) == 0){//判断express长度是否为偶数
            return false;
        }
        for(int i = 0; i < exp.length; i += 2){
            if (exp[i] != '1' && exp[i] != '0'){
                return false;
            }
        }
        for (int i = 1; i < exp.length; i += 2){
            if ((exp[i] != '&') && (exp[i] != '|') && (exp[i] != '^')){
                return false;
            }
        }
        return true;
    }

    //按符号划分，express中有几个符号就有几种划分，
    // 把每种划分能够得到最终desired值的种数加起来就是总的种数
    // ^ ,desired=true 左真右假，左假右真；desired=false 左假右假，左真右真
    // | ,desired=true 左假右真，左真右假，左真右真；desired=false 左假右假
    // & ,desired=true 左真右真；desired=false 左假右真，左真右假,左假右假
    public int num1(String express, boolean desired){
        if (express == null || express.equals("")){
            return 0;
        }
        char[] exp = express.toCharArray();
        if (!isValid(exp)){
            return 0;
        }
        return process(exp, desired, 0, exp.length - 1);
    }

    public int process(char[] exp, boolean desired, int sIndex, int eIndex){
        if (sIndex == eIndex){
            if (exp[sIndex] == '1'){
                return desired ? 1 : 0;
            }else {
                return desired ? 0 : 1;
            }
        }
        int res = 0;
        if (desired){
            for (int i = sIndex + 1; i < exp.length; i += 2){
                switch (exp[i]){
                    case '&':
                        res += process(exp, true, sIndex, i - 1) *
                                process(exp, true, i + 1, eIndex);
                        break;
                    case '|':
                        res += process(exp,true, sIndex, i - 1) *
                                process(exp, false, i + 1, eIndex);
                        res += process(exp, true, sIndex, i - 1) *
                                process(exp, true, i + 1, eIndex);
                        res += process(exp, false, sIndex, i - 1) *
                                process(exp, true, i + 1, eIndex);
                        break;
                    case '^':
                        res += process(exp, true, sIndex, i - 1) *
                                process(exp, true, i + 1, eIndex);
                        res += process(exp, false, sIndex, i - 1) *
                                process(exp, false, i + 1, eIndex);
                        break;
                }
            }
        }else {
            for (int i = sIndex + 1; i < exp.length; i += 2){
                switch (exp[i]){
                    case '&':
                        res += process(exp, false, sIndex, i - 1) *
                                process(exp, false, i + 1, eIndex);
                        res += process(exp, false, sIndex, i - 1) *
                                process(exp, true, i + 1, eIndex);
                        res += process(exp, true, sIndex, i - 1) *
                                process(exp, false, i + 1, eIndex);
                        break;
                    case '|':
                        res += process(exp, false, sIndex, i - 1) *
                                process(exp, false, i + 1, eIndex);
                        break;
                    case '^':
                        res += process(exp, false, sIndex, i - 1) *
                                process(exp, false, i + 1, eIndex);
                        res += process(exp, true, sIndex, i - 1) *
                                process(exp, true, i + 1, eIndex);
                        break;
                }
            }
        }
        return res;
    }

    //动态规划
    //t[j][i] 表示express[j...i]组成true的种数
    //f[j][i] 表示express[j..i]组成false的种数
    public int num2(String express, boolean desired){
        if (express == null || express.equals("")){
            return 0;
        }
        char[] exp = express.toCharArray();
        if (!isValid(exp)){
            return 0;
        }
        int[][] t = new int[exp.length][exp.length];
        int[][] f = new int[exp.length][exp.length];
        t[0][0] = exp[0] == '1' ? 1 : 0;
        f[0][0] = exp[0] == '0' ? 1 : 0;
        for (int i = 2; i < exp.length; i += 2){//i列
            t[i][i] = exp[i] == '1' ? 1 : 0;
            f[i][i] = exp[i] == '0' ? 1 : 0;
            for (int j = i - 2; j >= 0; j -= 2){
                //相当于更新j行，之所以倒着更新是因为i表示划分的后半部分第一个数字
                for (int k = j; k < i; k += 2){//k表示数字为，从0到结尾i
                    if (exp[k + 1] == '&'){
                        t[j][i] += t[j][k] * t[k + 2][i];
                        f[j][i] += f[j][k] * t[k + 2][i] + t[j][k] * f[k + 2][i] + f[j][k] * f[k + 2][i];
                    }else if (exp[k + 1] == '|'){
                        t[j][i] += t[j][k] * (t[k + 2][i] + f[k + 2][i]) + f[j][k] * t[k + 2][i];
                        f[j][i] += f[j][k] * f[k + 2][i];
                    }else {
                        t[j][i] += t[j][k] * f[k + 2][i] + f[j][k] * t[k + 2][i];
                        f[j][i] += t[j][k] * t[k + 2][i] + f[j][k] *f[k + 2][i];
                    }
                }
            }
        }
        return desired ? t[0][t.length - 1] : f[0][t.length - 1];
    }
}
