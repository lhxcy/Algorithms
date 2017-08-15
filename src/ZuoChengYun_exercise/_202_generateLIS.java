package ZuoChengYun_exercise;
/**
 * 目的：最长递归子序列    给定arr，返回arr的最长递增子序列
 * Created by XCY on 2017/8/8.
 *
 */
public class _202_generateLIS {
    public static int [] getdp1(int []arr){//O(n^2)
        int [] dp = new int[arr.length];
        for (int i = 0;i < arr.length; i++){
            dp[i] = 1;
            for (int j = 0; j < i; j++){
                if (arr[i] > arr[j]){
                    dp[i] = Math.max(dp[i],dp[j] + 1);
                }
            }
        }
        for (int i = 0; i < dp.length; i++){
            System.out.print(dp[i] + " ");
        }
        System.out.println();
        return dp;
    }

    public static int[] getdp2(int [] arr){//O(N*log(N))
        int [] dp = new int[arr.length];
        int [] ends = new int[arr.length];
        //ends[b] == c 表示遍历到目前为止，在所有长度为b+1的递增序列中，最小的结尾数是c
        ends[0] = arr[0];//初始化为arr[0]
        dp[0] = 1;
        int right = 0;
        int low = 0;//
        int high = 0;//这三个变量用于二分查找
        int mid = 0;//
        for (int i = 1; i < arr.length; i++){
            low = 0;
            high = right;
            while (low <= high){
                mid = (low + high)/2;
                if (arr[i] < ends[mid]){
                    high = mid - 1;
                }else{
                    low = mid + 1;
                }
            }
            right = Math.max(right,low);//找到前面第一个大于或等于它的数的位置
            ends[low] = arr[i];
            dp[i] = low + 1;
        }
        for (int i = 0; i < dp.length; i++){
            System.out.print(dp[i] + " ");
        }
        System.out.println();
        return dp;
    }

    public static int [] generateLIS(int [] arr, int [] dp){
        int len = 0;//最长递增子序列长度
        int index = 0;
        for (int i = 0; i < dp.length; i++){
            if (dp[i] > len){
                len = dp[i];
                index = i;
            }
        }
        int [] lis = new int[len];
        lis[--len] = arr[index];
        for (int i = index - 1; i >= 0; i--){//这里可以让i= index，不符合if语句而跳过
            if (arr[i] < arr[index] && dp[i] == dp[index] - 1){
                lis[--len] = arr[i];
                index = i;
            }
        }
        return lis;
    }

    public static int[] lis1(int [] arr){
        if (arr == null || arr.length == 0){
            return null;
        }
        int [] dp = getdp1(arr);
        return generateLIS(arr, dp);
    }

    public static int[] lis2(int []arr){
        if (arr == null || arr.length == 0){
            return null;
        }
        int [] dp = getdp2(arr);
        return generateLIS(arr, dp);
    }

    public static void main(String [] args){
        int [] arr = {2,1,5,3,6,4,8,9,7};
        int [] lis = lis2(arr);
        for (int i = 0; i < lis.length; i++){
            System.out.print(lis[i] + " ");
        }
    }

}
