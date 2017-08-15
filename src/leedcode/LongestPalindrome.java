package leedcode;

import static algorithms.Print.print;
import static algorithms.Print.println;

/**
 * Created by XCY on 2017/3/28.
 */
public class LongestPalindrome {
    public static String longestPalindrome(String s) {
        if (s==null&&s.length()==0)
            return "";
        String str="";
//        int len=s.length();
        char []strArr=s.toCharArray();
        int len=strArr.length;
        int startIndex=0;

        int max=1;
        boolean flag=false;
        for (int i=1;i<len;i++){
            int templen=0;
            if(flag==true)
                break;
            if (i-1>=0&&strArr[i]==strArr[i-1]){
//                println("i="+i);
//                println("333333");
                int pre=i-1;
//                println("pre="+pre);
                int nex=i;
                templen+=2;
//                println("templen1="+templen);
                while (pre>0&&nex<len-1){
//                    println("88888");
//                    if (strArr[--pre]==strArr[++nex])
                    if (strArr[--pre]==strArr[++nex])
                    {
                        templen+=2;
//                        println("hhhhhhhh");
                    }
                    else {
                        if (templen>max){
                            max=templen;
                            templen=0;
                            startIndex=pre+1;
//                            print("llllll");
//                            println("startIndex1"+startIndex);
                            break;
                        }
                        else {
                            templen=0;
                            break;
                        }
                    }
                    if((nex)==len){
//                        println("kkkkkkk");
                        flag=true;
                        break;
                    }
                }
                if (templen>max){
                    max=templen;
                    templen=0;
                    startIndex=pre;
                }
                else templen=0;
//                println("templen99="+templen);
//                println("max1="+max);
            }
            if (i+1<len&&strArr[i+1]==strArr[i-1]){
//                println("44444");
//                println("i2="+i);
                templen+=3;
                int pre=i-1;
                int nex=i+1;
                while (pre>0&&nex<len-1){
                    if (strArr[--pre]==strArr[++nex]){
                        templen+=2;
//                        println("templen99="+templen);
//                        println("pre99="+pre);
//                        println("nex99="+nex);
                    }
                    else {
                        if (templen>max){
                            max=templen;
                            templen=0;
                            startIndex=pre+1;
//                            println("22222222");
//                            println("startIndex2"+startIndex);
                            break;
                        }
                        else {
                            templen=0;
                            break;
                        }
                    }
                    if((nex)==len){
                        flag=true;
                        templen=0;
                        break;
                    }
                }
                if (templen>max){
                    max=templen;
                    templen=0;
                    startIndex=pre;
                }
//                templen=0;
//                println("templen99="+templen);
//                println("max2="+max);
            }
        }
//        println(max);
//        print(startIndex);
        str=s.substring(startIndex,startIndex+max);
        return  str;

    }
    public static void main(String [] args){
        String str="babad";
        String str1="cbbc";
        String str2="cccc";
        String str3="aaabaaaa";
        String result=longestPalindrome(str);
        print(result);

    }
}
