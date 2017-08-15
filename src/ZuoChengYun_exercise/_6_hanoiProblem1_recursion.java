package ZuoChengYun_exercise;
/**
 * 汉诺塔
 * 规则：限制不能从最左移到最右，也不能从最右移到最左
 * Created by XCY on 2017/7/5.
 * 思想：递归
 * 1--N-1左 --> 右
 * N左 -->中
 * 。。。
 */
public class _6_hanoiProblem1_recursion {
    public int hanoiProblem1(int num, String left, String mid, String right){
        if (num < 1){
            return 0;
        }
        return process(num, left, mid, right, left, right);
    }

    public int process(int num, String left, String mid, String right, String from, String to){
        if (num == 1){
            if (from.equals(mid)||to.equals(mid)){
                System.out.println("Move 1 from "+from+" to "+to);
                return 1;
            }else {
                System.out.println("Move 1 from "+from+" to "+to+"mid");
                System.out.println("Move 1 from "+mid+" to "+to);
                return 2;
            }
        }
        if (from.equals(mid)||to.equals(mid)){
            String another = (from.equals(left)||to.equals(left)?right:left);//表示空闲辅助“柱子”
            int part1 = process(num-1, left, mid, right, from, another);
            int part2 = 1;
            System.out.println("Move "+ num+ " from "+from+" to "+to);
            int part3 = process(num-1, left, mid, right, another, to);
            return part1 + part2 + part3;
        }else {
            int part1 = process(num-1, left, mid, right, from, to);
            int part2 = 1;
            System.out.println("Move "+num+" from "+from+" to "+mid);
            int part3 = process(num-1, left, mid, right, to, from);
            int part4 = 1;
            System.out.println("Move "+num+" from "+mid+" to "+to);
            int part5 = process(num-1, left, mid, right, from, to);
            return part1+part2+part3+part4+part5;
        }

    }
}
