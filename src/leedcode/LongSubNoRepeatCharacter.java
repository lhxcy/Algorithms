package leedcode;

import java.util.HashMap;
import static algorithms.Print.*;

/**
 * Created by XCY on 2017/3/25.
 */
public class LongSubNoRepeatCharacter {
    public static int lengthOfLongestSubstring2(String s){
        if (s==null) return  0;
        StringBuffer maxSubString=new StringBuffer("");
        char [] strCharArr=s.toCharArray();
        HashMap<Character,Integer> hash=new HashMap<Character,Integer>();
        int startIndex=-1;//不含有重复字符的字符串的下标，不一定最长
        int oristartIndex=startIndex;//起始字符串下标减一
        int maxLen=0;
        for (int index=0;index<s.length();index++){
            if (hash.containsKey(strCharArr[index])){
                int oriIndex=hash.get(strCharArr[index]);//含有重复字符时重复字符的下标
                if (oriIndex>startIndex)
                    startIndex=oriIndex;
            }
            if (index-startIndex>maxLen){
                maxLen=index-startIndex;
                oristartIndex=startIndex;
            }
            hash.put(strCharArr[index],index);

        }
        for (int index=oristartIndex+1;index<oristartIndex+maxLen+1;index++)
        {
            maxSubString.append(strCharArr[index]);//存放不重复字符串
        }
        return  maxLen;
    }
    public static int min(int a,int b){
        if(a>b)
            return b;
        else
            return a;
    }
    public static int lengthOfLongestSubstring(String s) {
        HashMap<Character,Integer> hash=new HashMap<Character,Integer>();
//        int result=0;
        if(s.length()==0||s==null)
            return 0;
        int max=0;
        int len=s.length();
        for (int j=0;j<min(len,256);j++){
            int count=0;
            boolean flag=false;
            for (int i=j;i<len;i++){
                char temp=s.charAt(i);
                if (hash.containsKey(temp)){
                    flag=true;
                    j=hash.get(temp);
                }
                else {
                    count++;
                    hash.put(temp,i);
                }
                if (flag&&j<len) {
                    if (count>max)
                        max=count;
//                    println(hash);
                    hash.clear();
//                    count=0;
                    break;
                }
            }
            if(count>max)
                max=count;
        }
        return max;


    }
    public static void main(String [] args){
        String str1="abcabcdegb";
        String str="pwwkew";
        String str2="ilstiirmgduyuftzxawvbjrrphjiwffgszzcisqoxlprqkqnloloaehrltzjahps";

        int result=lengthOfLongestSubstring2(str2);
        println(result);
//        println(str3.length());


    }
}
