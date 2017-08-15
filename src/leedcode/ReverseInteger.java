package leedcode;

import java.math.BigInteger;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/4/1.
 */
public class ReverseInteger {
    public static String Sreverse(String s){
        String str="";
        char [] strArr=s.toCharArray();
        int len=strArr.length;
        if (strArr[0]=='-'){
            for (int i=1,j=len-1;i<j;i++,j--){
                char temp=strArr[i];
                strArr[i]=strArr[j];
                strArr[j]=temp;
            }
        }
        else {
            for (int i=0,j=len-1;i<j;i++,j--){
                char temp=strArr[i];
                strArr[i]=strArr[j];
                strArr[j]=temp;
            }
        }
        str=new String(strArr);
        if (len==10){
            String max="2147483647";
            String min="-2147483648";
            if (str.compareTo(max)>0)
                return "";
        }
        if (len==11){
            String max="2147483647";
            String min="-2147483648";
            if (str.compareTo(min)>0)
                return "";

        }


        return  str;
    }
    public static int reverse(int x) {//-2147483648到2147483647


        String s=String.valueOf(x);
        String Sresult = Sreverse(s);
        if (Sresult=="")
            return 0;

//        BigInteger Iresult= new BigInteger(Sresult);
        int Iresult=Integer.parseInt(Sresult);
//        BigInteger Iresult=BigInteger.valueOf(Sresult).intValue();
//        if ( Iresult.compareTo() || Iresult.intValue() < Integer.MIN_VALUE)
//            return 0;
        return  Iresult;


    }
    public static void main(String[] args){
        int x= -2147483648;
        int x1=-12345;
        int result=reverse(x);
        println(result);

    }
}
