package leedcode;

import org.omg.CORBA.PUBLIC_MEMBER;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/4/3.
 */
public class Roman_to_Integer {
    public static class Node{
        int val;
        String str;
//        public Node(){
//            val=0;
//            str="";
//        }
    }
    public static Node partromanToInt(String []str,String s){
//        println(s);
        // boolean flag=false;
        Node node=new Node();
        int partresult=0;
        int len=str.length;
        for(int i=len-2;i>0;i--){
            if(s.indexOf(str[i])!=-1){
                if(s.indexOf(str[i])!=0)
                    continue;
                else{
                    s=s.replaceFirst(str[i],"");
//                    println(s);
                    partresult+=i*Integer.parseInt(str[len-1]);
                }

                // if(i==3||i==2|i==1||i==5||i==6||i==7||i==8)
                //     flag=true;
            }
        }
        node.val=partresult;
        node.str=s;
        return node;

    }
    public static int romanToInt(String s) {
//        println(s);
        String []elem1_10={"","I","II","III","IV","V","VI","VII","VIII","IX","1"};
        String []elem10_100={"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC","10"};
        String []elem100_1000={"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM","100"};
        String []elem1000_3000={"","M","MM","MMM","1000"};
        int result=0;
        int result0=0;
        int len=elem1000_3000.length;
        // boolean flag=false;
        for(int i=len-2;i>0;i--){
            if(s.indexOf(elem1000_3000[i])!=-1){
                if (s.indexOf(elem1000_3000[i])!=0)
                    continue;
                s=s.replaceFirst(elem1000_3000[i],"");
                result0+=i*Integer.parseInt(elem1000_3000[len-1]);
                // flag=true;
            }
        }
//        System.out.println(result0);

        Node result1=partromanToInt(elem100_1000,s);
        Node result2=partromanToInt(elem10_100,result1.str);
        Node result3=partromanToInt(elem1_10,result2.str);
        System.out.println(result1.val);
        System.out.println(result2.val);
        System.out.println(result3.val);
        result=result0+result1.val+result2.val+result3.val;
        return result;
    }
    public static void main(String[] args){
        String s="MCDXIV";
        String s1="MMMCDLX";
        int reault=romanToInt(s1);
        println(reault);
    }
}
