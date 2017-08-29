package ZuoChengYun_exercise;

/**
 * 目的：替换字符串连续出现的指定字符串
 * sre, from, to  把str中所有的from替换为to，from中没有重复字符，
 * 对于连续出现的from的部分要求只替换一个to字符串
 * Created by XCY on 2017/8/23.
 *  1：生成整形变量match，表示目前匹配到from的字符串的什么位置，初始化为0
 *  2：从左到右遍历str，假设当前遍历到str[i]
 *  3: 如果str[i] == from[match],match是from的最后一个位置，则从i向左的m个位置置0
 *  m为from长度，设置完后match=0；如果match不是from最后一个位置，令match++，继续遍历
 *  4：如果str[i] ！= from[match]，令match=0，此时如果str[i] == from[match],从当前位置继续匹配
 *  然后将连续0用to代替
 */
public class _251_replace {
    public String replace(String str, String from, String to){
        if (str == null || from == null || str.equals("") || from.equals("")){
            return str;
        }
        char[] chs = str.toCharArray();
        char[] chf = from.toCharArray();
        int match = 0;
        for (int i = 0; i < chs.length; i++){
            if (chs[i] == chf[match++]){
                if (match == chf.length){
                    clear(chs, i , chf.length);
                }
            }else {
                if (chs[i] == chf[0]) {
                    i--;
                }
                match = 0;
            }
        }
        String res = "";
        String cur = "";
        for (int i = 0; i < chs.length; i++){
            if (chs[i] != 0){
                cur = cur + String.valueOf(chs[i]);
            }
            if (chs[i] == 0 && (i == 0 || chs[i - 1] != 0)){
                res = res + cur + to;
                cur = "";
            }
        }
        if (!cur.equals("")){
            res = res + cur;
        }
        return res;
    }

    public void  clear(char[] chs, int end, int len){
        while (len-- != 0){
            chs[end--] = 0;
        }
    }

}
