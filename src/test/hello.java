
package test;
import java.util.*;
import java.awt.*;
import java.util.Arrays;

import static algorithms.Print.print;
import static algorithms.Print.println;
import static java.util.Arrays.sort;


/**
 * Created by XCY on 2017/3/1.
 */
public class hello {
   /* public static int binsearch(int a[],int key){
        int lo=0;
        int high=a.length-1;
        while(lo<=high){
            int mid=(lo+high)/2;
            if(key<a[mid]) high=mid-1;
            else if(key>a[mid]) lo=mid+1;
            else return mid;
        }
        return 0;
    }
    public static int [] twosum(int []nums ,int target){
//        nums.sort();
        Arrays.sort(nums);
        int n=nums.length;
        int [] b= new int[2];
        for (int i=0;i<n;i++){
//            System.out.println(i);
            int tar=target-nums[i];
            if(binsearch(nums,tar)!=0) {
//                System.out.println(i);
                b[0] = i;
                b[1] = binsearch(nums, tar);
                return b;
            }

        }
        return b;
    }*/
//   public static void sort(int []a){
//       int N=a.length;
//       for (int i=1;i<N;i++){
//           int temp=a[i];
//           int index=i;
//           for (int j=i;j>0;j--){
//                if (a[j]>a[j-1]){
//                    a[j]=a[j-1];
//                    index=j;
//                }
//
//           }
//           a[index]=temp;
//
//       }

////   }
//    public static void main(String [] args) {
//        int []a={6,5,7,3,8,1};
//        Arrays.sort(a);
//        for (int i=0;i<a.length;i++)
//                print(a[i]);
//        println();
//        double b=1.5;
//        print((int)b);

////        System.out.println("hello1");
//        int[] a = {2,7,8,5};
//        int target = 9;
//        int[] an = new int[2];
//        an = twosum(a, target);
//        for (int i = 0; i < 2; i++) {
//            System.out.println(an[i]);
//        }


//    }
//   public static int[] twosum(int[] numbers, int target) {
//        HashMap<Integer,Integer> hash = new HashMap<Integer,Integer>();
//        for(int i = 0; i < numbers.length; i++){
////            System.out.println(numbers[i]);
//            Integer diff = (Integer)(target - numbers[i]);
//            if(hash.containsKey(diff)){
////                System.out.println(hash.get(diff));
//                 int []toReturn = {hash.get(diff), i};
//                 return toReturn;
//        }

//        hash.put(numbers[i], i);

//    }

//        return null;

//}
//public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
//    int M=nums1.length;
//    int N=nums2.length;
//    int Len=M+N;
//    double mid=-1;
//    if(Len%2==0){
//        int mid1=getTopK(nums1,0,M-1,nums2,0,N-1,Len/2);
//        int mid2=getTopK(nums1,0,M-1,nums2,0,N-1,Len/2+1);
//        mid=(mid1+mid2)*1.0/2.0;
//
//    }
//   else
//        mid= getTopK(nums1,0,M-1,nums2,0,N-1,(Len+1)/2);
//    return mid;
//
//}
public static double findMedianSortedArrays(int[] a, int[] b){
    double mid=-1;
    int m=a.length;
    int n=b.length;
    int len=m+n;
    if(len%2==0){
        int mid1=getTopK(a,0,m-1,b,0,n-1,len/2);
        int mid2=getTopK(a,0,m-1,b,0,n-1,len/2+1);
        mid=(mid1+mid2)*1.0/2;
    }
    else{
        mid=getTopK(a,0,m-1,b,0,n-1,(len+1)/2);
    }
    return mid;

}

        private static int getTopK(int[] arrA, int sa, int ea, int[] arrB, int sb,int eb, int k) {

            int ma = (sa + ea) / 2;
            int mb = (sb + eb) / 2;
            int halflen=ma-sa+1+mb-sb+1;
            if (sa > ea) {
                return arrB[sb + k - 1];
            }
            if (sb > eb) {
                return arrA[sa + k - 1];
            }
            if (arrA[ma] >= arrB[mb]) {
                if (halflen > k) {
                    return getTopK(arrA, sa, ma - 1, arrB, sb, eb, k);
                } else {
                    return getTopK(arrA, sa, ea, arrB, mb + 1, eb, k - (mb + 1 - sb));
                }
            } else {
                if (halflen > k) {
                    return getTopK(arrA, sa, ea, arrB, sb, mb - 1, k);
                } else {
                    return getTopK(arrA, ma + 1, ea, arrB, sb, eb, k - (ma + 1 - sa));
                }
            }
        }


    public static void main(String [] args){
        int []a={1,3};
        int []b={2,4,5,6,7};
        double mid=findMedianSortedArrays(a,b);
        println(mid);

    }
}
