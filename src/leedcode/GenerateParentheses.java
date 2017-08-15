package leedcode;

import java.util.ArrayList;
import java.util.List;

import static algorithms.Print.print;

/**
 * Created by XCY on 2017/4/13.
 */
public class GenerateParentheses {
    /**
     * 起初想法是全排列然后判断是否符合条件，貌似时间复杂度略高
     * 此方法是取自别人的方法
     * The idea here is to only add '(' and ')' that we know will guarantee us a solution
     * (instead of adding 1 too many close). Once we add a '(' we will then discard it and try a ')'
     * which can only close a valid '('. Each of these steps are recursively called.
     * @param n
     * @return
     */
    public static List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<String>();
        backtrack(list, "", 0, 0, n);
        return list;
    }

    private static void  backtrack(List<String> list, String str, int open, int close, int max){

        if(str.length() == max*2){
            list.add(str);
            return;//返回一层循环
        }

        if(open < max)
            backtrack(list, str+"(", open+1, close, max);
        if(close < open)
            backtrack(list, str+")", open, close+1, max);
        //close<open 保证拒绝")",只有在open增加时才能加入“）”
    }
    public  static void main(String [] args){
        int n=3;
        List<String> list = new ArrayList<String>();
        list=generateParenthesis(n);
        print(list);

    }
}
