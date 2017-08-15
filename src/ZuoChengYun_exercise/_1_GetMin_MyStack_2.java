package ZuoChengYun_exercise;
import java.util.Stack;

/**
 * 目的pop ,push ,getMin 操作时间复杂度O(1)
 * Created by XCY on 2017/7/3.
 * 建立两个栈stackData//用来正常存储数据,
 * stackMin//用来存储stackData中加入新元素时每一步的最小值,
 * 如果newnum<=stackMin的栈顶元素时，newnum加入stackMIn
 * 如果newnum>stackMin的栈顶元素时,把stackMin的栈顶元素重复压入stackMin
 * getMin直接从stackMin中取数据
 *
 * stackMin压入时稍占空间，但弹出操作稍微省时间
 */
public class _1_GetMin_MyStack_2 {
    private Stack<Integer> stackData;//用来正常存储数据
    private Stack<Integer> stackMin;//用来存储stackData中加入元素时每一步的最小值
    public _1_GetMin_MyStack_2(){
        this.stackData=new Stack<Integer>();
        this.stackMin=new Stack<Integer>();
    }

    public void push(int newnum){
        if (this.stackMin.isEmpty()){
            this.stackMin.push(newnum);
        }else if (newnum<this.getMin()){
            this.stackMin.push(newnum);
        }else {
            int newMin = this.stackMin.peek();
            this.stackMin.push(newMin);
        }
        this.stackData.push(newnum);
    }

    public int pop(){
        if (this.stackData.isEmpty()){
            throw new RuntimeException("Your stack is empty!!!");
        }
        this.stackMin.pop();//这里必须弹出，否则两个栈长度不一
        return this.stackData.pop();
    }

    public int getMin(){
        if (this.stackMin.isEmpty()){
            throw new RuntimeException("Your stack is empty!!!");
        }
        return this.stackMin.peek();
    }
}
