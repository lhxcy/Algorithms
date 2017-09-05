package test;

/**
 * Created by XCY on 2017/9/3.
 *      StringBuffer线程安全
 *      StringBuilder线程不安全
 *      建议使用StringBuilder
 *      升级三个因素：提高效率，简化书写，提高安全性
 *          int num = Integer.parseInt("123");//将字符串123转换为数字123
 *
 *
 */
public class ZiFuChuanString {
    public static String getMaxSubString(String str1, String str2){
        for (int i = 0; i < str2.length(); i++){
            for (int j = 0,z = str2.length() - i; z != str2.length() + 1; j++, z++){
                String temp = str2.substring(j,z);
//                System.out.println(temp);
                if (str1.contains(temp))
                    return temp;
            }
        }
        return null;
    }
    public static void main(String[] args){

        String s1 = "abcdef";
        String s2 = new String("abc");

        System.out.println(getMaxSubString(s2,s1));

//
//        String s3 = "abc";
//        System.out.println(s1 == s2);
//        System.out.println(s1 == s3);
    }
}
