package leedcode;

/**
 * 目的：将矩阵顺时针旋转90°
 * Created by XCY on 2017/8/14.
 * 先将矩阵转置，然后交换列的位置
 */
public class _48_RotateImage {
    public void rotate(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return;
        }
        int n = matrix.length;
        transpose(matrix);
        for(int i = 0, j = n - 1; i < j; i++, j--){
            swapArr(matrix, i, j);
        }

    }
    public void transpose(int[][] m){
        // int n = m.length;
        int temp;
        for(int i = 0; i < m.length; i++){
            for(int j = i; j < m.length;j++){
                temp = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = temp;
            }
        }
    }

    public void swapArr(int[][] m, int i, int j){//交换第i列与第j列
        int temp;
        // int n = m.length;
        for(int k = 0; k < m.length; k++){
            temp = m[k][i];
            m[k][i] = m[k][j];
            m[k][j] = temp;
        }
    }
}
