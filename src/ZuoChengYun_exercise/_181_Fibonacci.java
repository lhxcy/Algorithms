package ZuoChengYun_exercise;
/**
 * 目的：1 斐波那契 第N项
 *       2 台阶走法 N阶，可走1，2
 *       3 求n年后母牛数
 * 实现O(log(N))时间复杂度
 * Created by XCY on 2017/8/7.
 */
public class _181_Fibonacci {

    public int Fibonacci1(int n){//O(2^n)
        if (n < 1){
            return 0;
        }
        if (n == 1 || n == 2){
            return 1;
        }
        return Fibonacci1(n-1)+Fibonacci1(n-2);
    }

    public int Fibonacci2(int n){//O(N)
        if (n < 1){
            return 0;
        }
        if (n == 1 || n == 2){
            return 1;
        }
        int res = 1;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++){
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    //可以用矩阵相乘的方法，若严格遵守 f(n) = f(n-1) + f(n-2)
    //{f(n),f(n-1)} = {f(n-1),f(n-2)* {{a,b},{c,d}},可以求得a,b,c = 1,d = 0;
    //推理的{f(n),f(n-1)} = {f(2),f(1)}*{{1,1},{1,0}}^(n-2) = (1,1)*{{1,1},{1,0}}^(n-2)
    //求矩阵n次方时间复杂度O(log(N))

    public int[][] matrixPower(int[][] m, int p){//求矩阵m的p次方 O(log(N))
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i ++){//将res设为单位矩阵
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for ( ; p != 0; p >>= 1){
            if ((p & 1) != 0){
                res = muliMatrix(res,tmp);
            }
            tmp = muliMatrix(tmp,tmp);
        }
        return res;
    }

    public int[][] muliMatrix(int[][] m1, int[][] m2){
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++){
            for (int j = 0; j < m2[0].length; j++){
                for (int k = 0; k < m2.length; k++){
                    res[i][j] += m1[i][k]*m2[j][k];
                }
            }
        }
        return res;
    }

    public int Fibonacci3(int n){
        if (n < 1){
            return 0;
        }
        if (n == 1 || n == 2){
            return 1;
        }
        int[][] base = {{1,1},{1,0}};
        int[][] res = matrixPower(base,n-2);
        return res[0][0] + res[1][0];//乘之后的结果
    }

    //补充问题1：若只有1阶，则1种方法，若2阶，则2种方法；若N阶，最后跳上N阶的情况，
    //要么从N-2，直接跳2步，要么直接从N-1，直接跳1步，所以台阶有N阶的方法数为跨到
    // N-1阶的方法数加上跨到N-2阶的方法数，即S(N) = S(N-1) + S(N-2),和上面一样，只是初始值不一样

    public int s1(int n){//O(2^n)
        if ( n < 1){
            return 0;
        }
        if (n == 1 || n ==2){
            return n;
        }
        return s1(n-1) + s1(n-2);
    }

    public int s2(int n){//O(N)
        if ( n < 1){
            return 0;
        }
        if (n == 1 || n ==2){
            return n;
        }
        int res = 2;
        int pre = 1;
        int tmp = 0;
        for (int i = 3;i <= n; i++){
            tmp = res;
            res = res + pre;
            pre = res;
        }
        return res;
    }

    public int s3(int n){//思想同Fibonacci3,{{1,1},{1,0}}为状态矩阵，根据公式求得
        if (n < 1){
            return 0;
        }
        if (n == 1 || n == 2){
            return n;
        }
        int[][] base = {{1,1},{1,0}};
        int[][] res = matrixPower(base,n-2);
        return 2*res[0][0] + res[1][0];
    }

    //补充问题2：成熟母牛每年生1头小母牛，永远不会死，每只小母牛3年之后成熟，初始有1头成熟母牛
    //c(1) = 1; c(2) = 2; c(3) = 3   c(n) = c(n-1) + c(n-3)   前一年牛加上新牛

    public int c1(int n){//O(2^n)
        if ( n < 1){
            return 0;
        }
        if (n == 1 || n ==2 || n ==3){
            return n;
        }
        return c1(n-1) + c1(n-3);
    }

    public int c2(int n){//O(N)
        if ( n < 1){
            return 0;
        }
        if (n == 1 || n ==2 || n == 3){
            return n;
        }
        int res = 3;
        int pre = 2;
        int prepre = 1;
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 4;i <= n; i++){
            tmp1 = res;
            tmp2 = pre;
            res = res + prepre;
            pre = tmp1;
            prepre = tmp2;
        }
        return res;
    }

    //因为 c(n) = c(n-1) + c(n-3) c(n)跨过c(n-1) , c(n-2) , c(n-3),所以状态矩阵为3*3
    //用上面方法求得为{{1,1,0},{0,0,1},{1,0,0}}

    public int c3(int n){
        if (n < 1){
            return 0;
        }
        if (n == 1 || n ==2 || n == 3){
            return n;
        }
        int [][] base = {{1,1,0},{0,0,1},{1,0,0}};
        int [][] res = matrixPower(base, n-3);
        return 3*res[0][0] + 2*res[1][0] + res[2][0];
    }

}
