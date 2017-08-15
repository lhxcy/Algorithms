package leedcode;

import java.util.Stack;

import static algorithms.Print.println;

/**
 * Created by XCY on 2017/4/12.
 */
public class ValidParentheses {
    public static boolean isValid (String s) {
        if (s.length()<2)
            return false;
        if (s.length()%2!=0)
            return false;
//        boolean flag = true;
        Stack stack = new Stack();
        int len = s.length();
        char [] strCharArr=s.toCharArray();
        for (int i = 0; i<len;i++){
            if (strCharArr[i]=='('||strCharArr[i]=='['||strCharArr[i]=='{')
                stack.push(strCharArr[i]);
            if (strCharArr[i]==')'||strCharArr[i]==']'||strCharArr[i]=='}'){
                if (stack.isEmpty())
                    return false;
                char temp=(char)stack.pop();
//                println(temp);
                if ((temp=='('&&strCharArr[i]!=')')||(temp=='['&&strCharArr[i]!=']')||(temp=='{'&&strCharArr[i]!='}'))
                    return false;

            }
        }
        if (stack.isEmpty())
            return true;
        else
            return false;

    }
    public static void main(String [] args){
        String s="((({{[[]]}})))";
        boolean flag=isValid(s);
        println(flag);
    }
}
