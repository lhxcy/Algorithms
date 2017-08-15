package leedcode;
import java.util.*;
import java.awt.*;
import java.util.Arrays;

import static algorithms.Print.print;
import static algorithms.Print.println;
import static java.util.Arrays.sort;


/**
 * Created by XCY on 2017/3/26.
 */
public class Median_TwoSortedArray {
    public static double findMedianSortedArrays(int[] a, int[] b){
        double mid=-1;
        int m=a.length;
        int n=b.length;
        int len=m+n;
        if(len%2==0){
            int mid1=FindKthElem(a,0,m-1,b,0,n-1,len/2);
            int mid2=FindKthElem(a,0,m-1,b,0,n-1,len/2+1);
            mid=(mid1+mid2)*1.0/2;
        }
        else{
            mid=FindKthElem(a,0,m-1,b,0,n-1,(len+1)/2);
        }
        return mid;

    }
    public static int  FindKthElem(int [] a,int alow,int ahigh,int []b,int blow,int bhigh,int k){
        int amid=(alow+ahigh)/2;
        int bmid=(blow+bhigh)/2;
        int halflen=amid-alow+1+bmid-blow+1;
        if (alow>ahigh){
            return b[blow+k-1];
        }
        if (blow>bhigh){
            return a[alow+k-1];
        }
        if (a[amid]>=b[bmid]){
            if (halflen>k){
                return FindKthElem(a,alow,amid-1,b,blow,bhigh,k);
            }
            else
                return FindKthElem(a,alow,ahigh,b,bmid+1,bhigh,k-(bmid-blow+1));
        }
        else {
            if (halflen>k){
                return FindKthElem(a,alow,ahigh,b,blow,bmid-1,k);
            }
            else
                return FindKthElem(a,amid+1,ahigh,b,blow,bhigh,k-(amid-alow+1));
        }


    }
    public static void main(String [] args){
        int []a={1,3};
        int []b={2,4,5,6,7};
        double mid=findMedianSortedArrays(a,b);
        println(mid);

    }
}
