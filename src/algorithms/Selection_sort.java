package algorithms;

import java.util.Comparator;

import static algorithms.Print.print;
import static algorithms.Print.println;

/**
 * Created by XCY on 2017/3/26.
 */
public class Selection_sort {//选择排序
    public static void sort(Comparable []a){
        int N=a.length;
        for (int i=0;i<N;i++){
            int min=i;
            for (int j=i+1;j<N;j++){
                if(less(a[j],a[min]))
                    min=j;
            }
            exch(a,i,min);
        }

    }
    private static  boolean less(Comparable v,Comparable w){
        return v.compareTo(w)<0;
    }
    private static void exch(Comparable [] a,int i,int j){
        Comparable t=a[i];a[i]=a[j];a[j]=t;
    }
    private static void show(Comparable []a){
        for (int i =0;i<a.length;i++){
            print(a[i]+" ");
            println();
        }
    }
    public static boolean issorted(Comparable []a){
        for (int i=1;i<a.length;i++){
            if(less(a[i],a[i-1])) return false;

        }
        return true;
    }
    public  static void main(String [] args){
        Integer []a={6,5,7,3,8,1};
        sort(a);
        for (int i=0;i<a.length;i++)
            print(a[i]);
    }
}
