package leedcode;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/5/23.
 */
public class _28_Implement_strStr {
    public static int strStr(String haystack, String needle) {
        int len_hay=haystack.length();
        int len_needle=needle.length();
        if (len_hay<len_needle)
            return -1;
//        if (len_hay==len_needle)
//            if (haystack.equals(needle))
//                return 0;
//            else return -1;
        if(len_hay==0)
            return 0;
        for (int i =0; i< len_hay-len_needle+1;i++){
            String temp=haystack.substring(i,i+len_needle);
            if (temp.equals(needle))
                return i;
        }
        return -1;
    }
    public static void main(String args[]){
        String str1="a";
        String str2="a";
        int temp=strStr(str1,str2);
        println(temp);

    }
}
