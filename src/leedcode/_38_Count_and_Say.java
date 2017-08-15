package leedcode;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/5/29.
 * 思想：用StringBuffer数组存储生成的数据，count记录前面有几个数字相同，若遇到不同的，插入count和前一个字符
 * 然后把count置为1，继续上面操作
 * 当且仅当j==str[i].length()-1时，flag置为true，为的是跳出循环后对最后的相同数据进行处理
 */
public class _38_Count_and_Say {
//    public
    public static String countAndSay(int n) {
        StringBuffer [] str =new StringBuffer[100];
        for (int i=0;i<str.length;i++)
            str[i]=new StringBuffer();
        str[1].append("1");
        str[2].append("11");
//        str[3].append("21");
        int i=2;
        while (i<=n){
            int count=1;int j;
            boolean flag=false;
            for (j=1;j<str[i].length();j++){
                if (str[i].charAt(j)==str[i].charAt(j-1)){
                    println("j:"+j);
                    println("count:"+count);
                    println("vvv:"+str[i].charAt(j));
                    count++;
                    if(j==str[i].length()-1)
                        flag=true;
                }
                if (str[i].charAt(j)!=str[i].charAt(j-1)){
                    println("kkkkkk");
                    str[i+1].append(count);
                    str[i+1].append(str[i].charAt(j-1));
                    count=1;
//                    flag=false;
                    if (j==str[i].length()-1&&flag==false){
                        println("ssssss");
                        str[i+1].append(count);
                        str[i+1].append(str[i].charAt(j));
                    }
                }
            }
            if (flag==true){//处理以相同数字结尾的情况
//                println();
                str[i+1].append(count);
                str[i+1].append(str[i].charAt(j-1));
            }
            ++i;
        }

        String s=new String(str[n]);
        return s;
    }
    public static void main(String [] args){
        int n=6;
        String str=countAndSay(n);
        println("ggggggggggg:"+str);
    }
}
