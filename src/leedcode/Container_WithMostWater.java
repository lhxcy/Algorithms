package leedcode;

/**
 * Created by XCY on 2017/4/3.
 */
public class Container_WithMostWater {
    public int maxArea(int[] height) {
        //参考别人算法实现的O(n),最初自己实现n2
        //算法思想，通过比较数组值的大小来控制下标移动，保留值大的移动值小的下标，因为容量需短板控制
        int len=height.length;
        if(len<2)
            return 0;
        int max=0;
        int left=0,right=len-1;
        while(left<right){
            if(max<Math.min(height[left],height[right])*(right-left))
                max=Math.min(height[left],height[right])*(right-left);
            if(height[left]<height[right])
                left++;
            else//(height[left]>height[right]) 使用else是为了让两值相等时下标移动或者可以让上面if语句中添加=号
                right--;
        }
        return max;
    }
}

