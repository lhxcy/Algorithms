package leedcode;

import static algorithms.Print.print;
import static algorithms.Print.println;

/**
 * Created by XCY on 2017/3/31.
 */
public class ZigZagConversion {
    public static String convert(String s, int numRows) {
        if(s.length()==0||s==null)
            return "";
        if (numRows==1)
            return s;

        String str="";
        char []strArr=s.toCharArray();
        int len=strArr.length;
        int k=numRows;
        int []label=new int [len];//辅助数组记录每一个字符应该属于的那一行的标记
        int count=1;
        boolean flag=false;
        char []result=new char[len];

        int temp=0;//记录result数组的下标
//        double curr=System.currentTimeMillis();
        if (numRows==2)
        {
            for (int i=0;i<len;i+=2)
                label[i]=1;
            for (int i=1;i<len;i+=2)
                label[i]=2;

//            for (int i=0;i<len;i++){
//                if ((i+1)%4==1)
//                    label[i]=1;
//                if ((i+1)%4==2)
//                    label[i]=2;
//                if ((i+1)%4==3)
//                    label[i]=2;
//                if ((i+1)%4==0)
//                    label[i]=1;
//            }

        }

        else {
            for(int i=0;i<len;i++){
                if (flag==false&&count<=k){
                    label[i]=count;
                    if (count==k){
                        flag=true;
//                      count--;
                        continue;
                    }
                    count++;
                }
                if (flag==true&&count>=2){
                    if (count==k)
                        count--;
                    label[i]=count;
                    if(count==2){
                        flag=false;
                        count--;
                        continue;
                    }
                    count--;
                }
            }
        }
            for (int i=1;i<=k;i++){
                for(int j=0;j<len;j++){
                    if(label[j]==i)
                    result[temp++]=strArr[j];
                }
            }
//        double now=System.currentTimeMillis();
//        println(now-curr);

//        for (int i=0;i<len;i++)
//            print(label[i]);
        str=new String(result);
        return str;
    }
    public static void main(String [] args){
        String str="PAYPALISHIRING";
        String str1="abc";
        String str2="1234";
        String result=convert(str2,2);
        println(result);

    }
}
