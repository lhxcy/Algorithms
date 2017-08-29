package ZuoChengYun_exercise;

/**
 * 目的：将数字字符串转换成整数值
 * Created by XCY on 2017/8/23.
 * 判断是否合法
 * 判断是否溢出
 */
public class _248_convertStrToInt {
    public boolean isValid(char[] chs){
        if (chs[0] != '-' &&(chs[0] < '0' || chs[0] > '9')){
            return false;
        }
        if (chs[0] == '-' && (chs.length == 1 || chs[1] == '0')){
            return false;
        }
        if (chs[0] == '0' && chs.length > 1){
            return false;
        }
        for (int i = 1; i < chs.length; i++){
            if (chs[i] < '0' || chs[i] > '9'){
                return false;
            }
        }
        return true;
    }

    public int convertStrToInt(String str){
        //因为负数比正数拥有更大的绝对值范围，所以转换过程中一律以负数的形式记录绝对值，
        // 最后决定返回什么
        if (str == null || str.equals("")){
            return 0;
        }
        char[] chs = str.toCharArray();
        if (!isValid(chs)){
            return 0;
        }
        boolean flag = chs[0] != '-';//记录数据是正还是负
        int res = 0;//结果
        int cur = 0;//当前值
        int minq = Integer.MIN_VALUE / 10;//当res小于minq时溢出，返回0
        int minr = Integer.MIN_VALUE % 10;//当res等于minq时，只能比较cur和minr，若cur<minr，返回0溢出
        for (int i = flag ? 0 : 1; i < chs.length; i++){
            cur = '0' - chs[i];
            if ((res < minq) || (res == minq && cur < minr)){
                return 0;
            }
            res += res * 10 + cur;
        }
        if (flag && res == Integer.MIN_VALUE){
            return 0;
        }
        return flag ? - res : res;
    }
}
