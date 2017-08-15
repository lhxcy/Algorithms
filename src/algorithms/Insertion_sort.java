package algorithms;
import java.util.Comparator;
import static algorithms.Print.print;
import static algorithms.Print.println;

/**
 * Created by XCY on 2017/3/26.
 */
public class Insertion_sort {
    public static void sort(Comparable []a){
        int N=a.length;
        for (int i=1;i<N;i++){
           Comparable temp=a[i];

           int j=i;
           for (;j>0&&less(temp,a[j-1]);j--){
               a[j]=a[j-1];

           }
           a[j]=temp;
//
       }

    }
    public static void sort2(Comparable []a){
        int N=a.length;
        for (int i=1;i<N;i++){
           for (int j=i;j>0&&less(a[j],a[j-1]);j--){
               exch(a,j,j-1);
           }
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
