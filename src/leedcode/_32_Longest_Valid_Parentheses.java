package leedcode;

import java.util.Stack;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/5/28.
 * copy 别人代码，自己没有做出来
 * 思想：初始化left=-1，默认刚开始合法，当出现‘）’且栈为空时，需更新left，记录下一次合法开始的位置的前一个位置，用于保证是2的倍数
 * 遇到‘）’且栈不为空，需要先弹出与之对应得‘（’来处理前一个‘（’与该‘）’的距离，若弹出后栈为空，则距离为i-left，
 * 若不为空，则距离为到前一个‘（'的距离，i-stack.peek（）
 * 需取max与以上两个值的最大一个
 */
public class _32_Longest_Valid_Parentheses {

    public static int longestValidParentheses(String s) {
        int max=0;
        int temp=s.length()-1;
        String str;
        for (int j=s.length()-1;j>0;--j){
            if (s.charAt(j)==')'){
//                println(j);
                temp=j;
                break;
            }
        }
        println(temp);
        if (temp<1) return 0;
        else str=s.substring(0,temp+1);
        println(str);

        Stack<Integer> stack=new Stack<Integer>();
        int left=-1;
        for(int i=0;i<str.length();i++){
            if (str.charAt(i)=='(') stack.push(i);
            else {
                if (stack.isEmpty()) left=i;
                else {
                    stack.pop();
                    if (stack.isEmpty()) max=Math.max(max,i-left);
                    else max=Math.max(max,i-stack.peek());
                }
            }
        }

        return  max;
    }
    public static void main(String []args){
        String str="()";
        int ans=longestValidParentheses(str);
        println(ans);


    }
}
