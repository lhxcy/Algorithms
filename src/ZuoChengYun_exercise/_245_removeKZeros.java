package ZuoChengYun_exercise;

/**
 * 目的：去掉字符串中连续出现k个0的子串
 * str = "A00B" ,k = 2   --->   "AB"
 * str = "A0000B000"  , k = 3   ---> "A0000B"
 * Created by XCY on 2017/8/21.
 */
public class _245_removeKZeros {
    public static String removeKZeros(String str, int k){
        if (str == null || str.length() == 0){
            return str;
        }
        char[] chs = str.toCharArray();
        int count = 0;//用于记录连续0的个数
        int start = -1;//用于记录连续0的开始
        for (int i = 0; i < chs.length; i++){
            if (chs[i] == '0'){
                count++;
                start = start == -1 ? i : start;
            }else {
                if (count == k){
                    while (count-- != 0){
                        chs[start++] = 0;
                    }
                }
                start = -1;
                count = 0;
            }
        }
        if (count == k){//处理0在最后面情况
            while (count-- != 0){
                chs[start++] = 0;
            }
        }
        return String.valueOf(chs);
    }
    public static void main(String[] args){
        String str = "A00B";
        String res = removeKZeros(str, 2);
        System.out.println(res);
    }
}
