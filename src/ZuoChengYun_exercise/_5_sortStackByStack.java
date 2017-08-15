package ZuoChengYun_exercise;
import java.util.Stack;

/**
 * 目的：利用辅助栈helpStack完成对栈stack的排序
 * Created by XCY on 2017/7/5.
 *思路：如果cur小于或等于helpStack栈顶元素，则将cur压入helpStack
 * 如果cur大于helpStack栈顶元素，则将helpStack的元素逐一弹出压入stack直到cur小于或等于helpStack的栈顶元素
 */
public class _5_sortStackByStack {
    public static void sortStackByStack(Stack<Integer> stack){
        Stack<Integer> helpStack = new Stack<Integer>();
        while (!stack.isEmpty()){
            int cur = stack.pop();
            while (!helpStack.isEmpty() && cur > helpStack.peek()){
                stack.push(helpStack.pop());
            }
            helpStack.push(cur);
        }

        while (!helpStack.isEmpty()){
            stack.push(helpStack.pop());
        }
    }

}
