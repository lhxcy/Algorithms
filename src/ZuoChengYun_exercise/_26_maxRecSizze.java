package ZuoChengYun_exercise;
import java.util.Stack;

/**
 *目的：给定一个整型矩阵，值只有1和0，求其中全为1的所有矩形中最大的矩形区域为1的数量
 * Created by XCY on 2017/7/11.
 * height[j]表示目前的底上（第一行），j位置往上（包括j位置）有多少连续的1
 */
public class _26_maxRecSizze {
    public int maxRecSize(int[][] map){
        //map.length 列数
        if (map == null || map.length == 0 || map[0].length == 0){
            return 0;
        }
        int maxArea = 0;
        int [] height = new int[map[0].length];
        for (int i = 0; i < map.length; i++){
            for (int j = 0;j <map[0].length; j++){
                height[j] = map[i][j] == 0 ? 0 : height[j]+1;
                //以第i行为底向上有几个连续的1
            }
            maxArea = Math.max(maxRecFromBottom(height),maxArea);//对每一行产生的height处理
        }
        return maxArea;
    }

    public int maxRecFromBottom(int[] height){
        //传递一个数组，返回以数组中数值构建的柱状图形成的长方形的最大面积
        if (height == null || height.length == 0){
            return 0;
        }
        int maxArea = 0;
        Stack<Integer> stack = new Stack<Integer>();//栈，存放数据下标，从栈顶到栈底的值是依次递减的
        for (int i = 0; i < height.length; i++){
            while (!stack.isEmpty() &&height[i] <= height[stack.peek()]){
                //若当前数值小于栈顶位置下标对应的数值，弹出栈顶元素记为j，新栈顶元素即位k
                //求出以弹出元素为轴向左扩展的面积(i-k-1) * height[j]
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 :stack.peek();
                int curArea = (i-k-1) * height[j];//关键公式
                maxArea = Math.max(curArea, maxArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){//栈不空时一次处理栈中所有元素
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (j-k-1) * height[j];
            maxArea = Math.max(maxArea,curArea);
        }
        return maxArea;

    }
    public int maxRecArea_leedcode(int[] height){//该方法取自leedcode，可以代替maxRecFromBottom
        if (height == null || height.length == 0){
            return 0;
        }
        int left = 0;
        int right = height.length-1;
        int maxArea = 0;
        while (left < right){
            if (maxArea < Math.min(height[left] , height[right])*(right - left)){
                maxArea = Math.min(height[left] , height[right])*(right - left);
            }
            if (height[left] < height[right]){
                left++;
            }else {
                right--;
            }
        }
        return maxArea;
    }
}
