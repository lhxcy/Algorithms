package leedcode;

import java.util.AbstractCollection;

/**
 * Created by XCY on 2017/7/12.
 */
public class _43_Multiply_Strings {
    public String multiply(String num1, String num2) {
        if (num1.length() == 0 || num2.length() == 0 || num1.equals("0") || num2.equals("0")){
            return "0";
        }
        if (num1.equals("1")){
            return num2;
        }
        if (num2.equals("1")){
            return num1;
        }
        String str = "";

        return str;
    }
    public String charStringMultiply(char ch, String str){
        StringBuffer strbuff = new StringBuffer();
        int tempMul = ch - '0';
        int flag = 0;
        for (int i = str.length()-1; i > -1; i--){
            int temp = (str.charAt(i) - '0') * tempMul + flag;
            flag = 0;
            if (temp > 9){
                flag = 1;
                temp -= 9;
            }
            strbuff.append(temp);
        }
        strbuff.reverse();
        String result = strbuff.toString();
        return result;
    }
}
