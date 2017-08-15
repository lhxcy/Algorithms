package leedcode;

import java.util.LinkedList;
import java.util.List;

import static algorithms.Print.print;
import static algorithms.Print.println;
/**
 * Created by XCY on 2017/4/7.
 */
public class LetterCombinationsofAPhoneNumber {
    public static StringBuffer []Twojoin(StringBuffer[] strbuffer,char [] ch1,int length){
        int count=0;
        int temp=0;
        int temp1=0;
        StringBuffer []strb=strbuffer.clone();//可以考虑用String保持不变
//        String []strb1=new String(strbuffer);
        boolean flag=false;

        int len1=ch1.length;
//        for (int i=0;i<len1;i++)
//            print(ch1[i]+" ");
//        println();
//        int len=strbuffer.length;
        for (int i=0;i<length;i++){
            for (int j=0;j<len1;j++){
                strbuffer[count++]=new StringBuffer(strb[i].append(ch1[j]));//定义新对象，否则等于的是引用
                strb[i].deleteCharAt(strb[i].length()-1);//删除添加的元素，保持strb不变
//                println(strb[i]);
            }

        }
        return  strbuffer;
    }
    public static List<String> letterCombinations(String digits) {
        List<String> list=new LinkedList();
        char [][]str={{},{},{'a','b','c'},{'d','e','f'},{'g','h','i'},{'j','k','l'},
                {'m','n','o'},{'p','q','r','s'},{'t','u','v'},{'w','x','y','z'}};

//        for (int i=0;i<str.length;i++){
//            print(str[i].length+"");
//            for (int j=0;j<str[i].length;j++)
//                print(str[i][j]+" ");
//        }
        int len=digits.length();
        if (len==0)
            return list;
//        println(len);
        int lenbuffer=1;
        int count=0;
        int length=1;
        int temp=0;
        char [] strCharArr=digits.toCharArray();
//        for (int i=0;i<len;i++)
//            println(strCharArr[i]);
//        print("svfnsnvnn");
        for (int i=0;i<len;i++){
            int k=(int)(strCharArr[i]-'0');
//            int len1=str[k].length;
            lenbuffer*=str[k].length;
//            println(lenbuffer);
        }
////            lenbuffer*=str[(int)strCharArr[i]-2].length;
//        println(lenbuffer);
        StringBuffer[]strbuffer=new StringBuffer[lenbuffer];
        for (int i=0;i<lenbuffer;i++){
            strbuffer[i]=new StringBuffer();//初始化很重要，要不会报错
//            println(strbuffer[i]);
        }
        for (int i=0;i<str[(int)(strCharArr[0]-'0')].length;i++){
            char [] ch=str[(int)(strCharArr[0]-'0')];
            strbuffer[count++].append(ch[i]);
        }
//        for (int i=0;i<lenbuffer;i++){
//            println(strbuffer[i]);
//        }
//        println(strbuffer.length);
        for (int i=1;i<len;i++){
            length*=str[(int)(strCharArr[i-1]-'0')].length;
            strbuffer=Twojoin(strbuffer,str[(int)(strCharArr[i]-'0')],length);
        }
        for (int i=0;i<lenbuffer;i++)
            list.add(new String(strbuffer[i]));
        return list;

    }
    public static void main(String[] args){
        List<String> list=new LinkedList();
        String s="234";
        list=letterCombinations("");
        println(list);


    }

}
