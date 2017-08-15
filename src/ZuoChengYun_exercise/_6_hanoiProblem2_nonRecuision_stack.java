package ZuoChengYun_exercise;
import java.util.Stack;

/**
 * 汉诺塔
 * 走出最小步数
 * Created by XCY on 2017/7/5.
 * 非递归,用栈模拟递归
 * leftStack, midStack, rightStack
 * 小压大原则：一个栈的栈顶元素a若要弹出压入到另一个栈，则a必须比另一个栈顶元素小
 * 相邻不可逆原则，leftStack-->midStack,和midStack-->leftStack过程互为逆过程
 *四个动作：leftStack-->midStack， midStack-->rightStack，rightStack-->midStack, midStack-->leftStack
 * 核心结论：1.第一个动作一定是leftStack-->midStack
 *           2.在走出最少步数的任意时刻，四个动作中只有一个动作不违反小压大和相邻不可逆原则，另外三个一定会违反
 *  所以每一时刻只有一个动作达标
 */


public class _6_hanoiProblem2_nonRecuision_stack {
    public enum Action{
        No, leftToMid, midToRight, rightToMid, midToLeft
    }

    public int hanoiProblem2(int num, String left, String mid, String right){
        Stack<Integer> leftStack = new Stack<Integer>();
        Stack<Integer> midStack = new Stack<Integer>();
        Stack<Integer> rightStack = new Stack<Integer>();
        leftStack.push(Integer.MAX_VALUE);
        midStack.push(Integer.MAX_VALUE);
        rightStack.push(Integer.MAX_VALUE);

        for (int i =num; i > 0; i--){
            leftStack.push(i);
        }

        Action [] record = {Action.No};
        int step = 0;

        while (rightStack.size() != num+1){//四步中只会执行一步加1，其他加0
            step += fStackTotStack(record, Action.midToLeft, Action.leftToMid, leftStack, midStack, left, mid);
            step += fStackTotStack(record, Action.leftToMid, Action.midToLeft, midStack, leftStack, mid, left);
            step += fStackTotStack(record, Action.rightToMid, Action.midToRight, midStack, rightStack, mid, right);
            step += fStackTotStack(record, Action.midToRight, Action.rightToMid, rightStack, midStack, right, mid);
        }
        return step;
    }

    public static int fStackTotStack(Action [] record, Action preNoAct, Action nowAct,
                                     Stack<Integer> fStack, Stack<Integer> tStack,
                                     String from, String to){//判断是否符合小压大原则和相邻不可逆原则
        if (record[0] != preNoAct&&fStack.peek() < tStack.peek()){
            tStack.push(fStack.pop());
            System.out.println("Move " + tStack.peek() + " from "+from + " to "+to);
            record[0] = nowAct;
            return 1;
        }
        return 0;
    }
}
