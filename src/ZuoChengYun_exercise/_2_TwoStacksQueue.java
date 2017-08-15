package ZuoChengYun_exercise;
import java.util.Stack;

/**
 * 目的：编写一个类，用两个栈实现队列，支持队列的基本操作
 *
 * Created by XCY on 2017/7/3.
 *
 */
public class _2_TwoStacksQueue {
    public Stack<Integer> stackPush;
    public Stack<Integer> stackPop;
    public _2_TwoStacksQueue(){
        stackPush=new Stack<Integer>();
        stackPop=new Stack<Integer>();
    }

    public void add(int pushInt){
        stackPush.push(pushInt);
    }

    public int poll(){
        if (stackPop.empty()&&stackPush.empty()){
            throw new RuntimeException("Queue is empty!!!");
        }else if (stackPop.empty()){
            while (!stackPush.empty()){
                stackPop.push(stackPush.pop());
            }
        }
        return stackPop.pop();
    }

    public int peek(){
        if (stackPop.empty()&&stackPush.empty()){
            throw new RuntimeException("Queue is empty!!!");
        }else if (stackPop.empty()){
            while (!stackPush.empty()){
                stackPop.push(stackPush.pop());
            }
        }
        return stackPop.peek();
    }
}
