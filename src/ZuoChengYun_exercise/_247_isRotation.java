package ZuoChengYun_exercise;

/**
 * 目的：判断两个字符串是否互为旋转词
 * "12345"的旋转词有"12345","23451","34512","45123","51234"
 * Created by XCY on 2017/8/21.
 * 如果长度不一样，一定不为旋转词
 * 字符串a,b,如果长度一样，先生成一个大字符串b2 = b + b,然后看a是否为b2的子串
 * 若是则返回true，否则返回false
 */
public class _247_isRotation {
    public static boolean isRotation(String str1, String str2){
        if (str1 == null || str2 == null || str1.length() != str2.length()){
            return false;
        }
        String b2 = str1 + str1;
        return b2.contains(str2);
    }

    public static void main(String[] args){
        String str1 = "12345";
        String str2 = "23451";
        System.out.println(isRotation(str1,str2));
    }
}
