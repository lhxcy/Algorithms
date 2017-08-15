package algorithms;

import java.util.Arrays;


import static algorithms.Print.println;

/**
 * Created by XCY on 2017/3/23.
 */
public class BinarySearch {
    public static int rank(int key,int [] a){
        int low=0;
        int high=a.length-1;
        while(low<=high){
            int mid=(low+high)/2;
            if(key>a[mid]) low=mid+1;
            else if(key<a[mid]) high=mid-1;
            else return a[mid];
        }
        return -1;
    }
    public static void main(String []args){
        int []test={2,7,9,8};
        Arrays.sort(test);
        int key=7;
        if(rank(key,test)!=-1)
            println(1);
//            System.out.println(1);
        else
            System.out.println(0);

    }
}
