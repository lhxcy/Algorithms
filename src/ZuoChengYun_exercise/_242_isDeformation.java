package ZuoChengYun_exercise;

/**
 * 判断两个字符串是否互为变形词
 * 如果str1和str2中出现的字符种类一样且每种字符出现的次数也一样，则str1和str2互为变形词
 * Created by XCY on 2017/8/16.
 */
public class _242_isDeformation {
    public boolean isDeformation(String str1, String str2){
        if (str1 == null || str2 ==null || str1.length() == 0 || str2.length() == 0){
            return false;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[] map = new int[256];
        for (char ch : chs1){
            map[ch]++;
        }
        for (char ch : chs2){
            if (map[ch]-- == 0){
                return false;
            }
        }
        return true;
    }

}
