package ZuoChengYun_exercise;
import java.util.Stack;

/**
 * 目的：逆转栈元素
 * Created by XCY on 2017/7/4.
 */
public class _3_Reversr_Stack {
    public static int getAndRemoveLastElement(Stack<Integer> stack){
        int result = stack.pop();
        if (stack.isEmpty()){
            return result;
        }else {
            int last=getAndRemoveLastElement(stack);
            stack.push(result);
            return last;//用来帮助返回最底层元素
        }
    }

    public static void reverse(Stack<Integer> stack){
        if (stack.isEmpty()){
            return;
        }else {
            int i = getAndRemoveLastElement(stack);
            reverse(stack);
            stack.push(i);
        }
    }
}
