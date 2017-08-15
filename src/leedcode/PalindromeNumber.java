package leedcode;

/**
 * Created by XCY on 2017/4/2.
 */
public class PalindromeNumber {
    public boolean isPalindrome(int x) {
        String str=String.valueOf(x);
        char []strArr=str.toCharArray();
        int len=strArr.length;
        for(int i=0,j=len-1;i<j;i++,j--){
            if(strArr[i]!=strArr[j])
                return false;
        }
        return true;

    }
    public static void main(String [] args){

    }
}
