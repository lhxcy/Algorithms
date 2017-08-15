package ZuoChengYun_exercise;
/**
 * 目的：排成一条直线的纸牌博弈问题
 * 给定数组arr，A，B两个玩家，A先拿，每次只能 拿最左或最右的数值
 * 两人都绝顶聪明，返回最后获胜者的分数
 * Created by XCY on 2017/8/10.
 *
 * firstGet(i,j)表示如果arr[i..j]这个排列上的纸牌被绝顶聪明的人先拿最终获得的分数
 * lastGet(i,j)表示如果arr[i..j]这个排列上的纸牌被绝顶聪明的人后拿最终获得的分数
 *
 * 动态规划
 * firstGet[i,j] 代表 firstGet(i,j)
 * lastGet(i,j) 代表 lastGet(i,j)
 */
public class _233_cardWin {

    public static int win1(int[] arr){
        if (arr == null || arr.length == 0 ){
            return 0;
        }
        return Math.max(firstGet(arr, 0, arr.length - 1), lastGet(arr, 0, arr.length - 1));
    }

    public static int firstGet(int[] arr, int i, int j){
        if (i == j){
            return arr[i];
        }
        return Math.max(arr[i] + lastGet(arr, i + 1, j), arr[j] + lastGet(arr, i, j - 1));
    }
    public static int lastGet(int[] arr, int i, int j){
        if (i == j){
            return 0;
        }
        //因为对手也是厉害的，所以会把差的留给玩家，
        // 所以返回Math.min(firstGet(arr, i + 1, j), firstGet(arr, i, j - 1));
        return Math.min(firstGet(arr, i + 1, j), firstGet(arr, i, j - 1));
    }

    //动态规划
    public static int win2(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int[][] firstGet = new int[arr.length][arr.length];
        int[][] lastGet = new int[arr.length][arr.length];
        for (int j = 0; j < arr.length; j++){
            firstGet[j][j] = arr[j];
            for (int i = j - 1; i >= 0; i--){
                //两个语句顺序无所谓
                firstGet[i][j] = Math.max(arr[i] + lastGet[i + 1][j], arr[j] + lastGet[i][j - 1]);
                lastGet[i][j] = Math.min(firstGet[i + 1][j], firstGet[i][j - 1]);
            }
        }
        return Math.max(firstGet[0][arr.length - 1], lastGet[0][arr.length - 1]);
    }

    public static void main(String[] args){
        int[] arr = {1,2,100,4,8,5,9,3};
        System.out.println(win1(arr));
        System.out.println(win2(arr));
    }
}
