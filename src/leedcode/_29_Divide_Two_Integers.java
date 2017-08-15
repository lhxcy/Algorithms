package leedcode;

import static algorithms.Print.println;


/**
 * Created by XCY on 2017/5/25.
 */
public class _29_Divide_Two_Integers {
    public static int divide_zhengshu(int dividend,int divisor,int flag){//flag用来表示最大的值是来自最小负数的转换还是最大正数
        println("kkkkk:"+dividend+"gggg:"+divisor);
        int count=0;

        if (flag==1){
            dividend=dividend-divisor+1;
            count+=1;
//            println("count:"+count);
        }

        while(dividend>=divisor){
            int tempcount=1;
            long temp=divisor;
//            int n =0;
            while ((dividend >= (temp<<1))){
                temp<<=1;
                tempcount<<=1;
//                if (temp>1073741824)
//                    break;
//                println("tempcount:"+tempcount);
//                println("temp："+temp);
//                n++;

            }
//            println("nnn:"+tempcount);
            dividend-=temp;
            count+=tempcount;
//            break;
        }
        return count;

    }

    public static int divide(int dividend, int divisor) {
        int ans=0;
        if (dividend==0)
            return 0;
        if (dividend==Integer.MIN_VALUE&&divisor==-1)//-2147483648/-1==2147483647
            return Integer.MAX_VALUE;
        if (divisor==Integer.MAX_VALUE){
            if (dividend==Integer.MAX_VALUE)
                return 1;
            if(dividend==Integer.MIN_VALUE)
                return -1;
            else return 0;
        }
        if (divisor==Integer.MIN_VALUE)
            if (dividend==Integer.MIN_VALUE)
                return 1;
            else return 0;

        if (dividend==Integer.MIN_VALUE){

            if (divisor>0){
//                println("mmmm1:"+dividend);
                ans=-divide_zhengshu(-(dividend+1),divisor,1);
            }

            if (divisor<0&&divisor>Integer.MIN_VALUE){
//                println("mmmm2:"+dividend);
                ans=divide_zhengshu(-(dividend+1),-divisor,1);
            }

        }

        if (dividend<0&&dividend>Integer.MIN_VALUE){
            if (divisor>0)
                ans=-divide_zhengshu(-dividend,divisor,0);
            if (divisor<0&&divisor>Integer.MIN_VALUE)
                ans=divide_zhengshu(-dividend,-divisor,0);
        }
        if (dividend>0)
        {
            if (divisor>0)
                ans=divide_zhengshu(dividend,divisor,0);
            if (divisor<0&&divisor>Integer.MIN_VALUE)
                ans=-divide_zhengshu(dividend,-divisor,0);
        }
        return ans;

    }
    public  static void main(String args[]){
        int a=Integer.MIN_VALUE;
        int b=-1;
        int temp=divide(a,b);
        println(temp);

    }
}
