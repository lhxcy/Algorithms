package leedcode;

/**
 * Created by XCY on 2017/4/4.
 */
public class LongestCommonPrefix {
    public int TwolongestCommonPrefix(String s1,String s2){
        int len1=s1.length();
        int len2=s2.length();
        // if(len1==1&&len2==1&&k>2)
        //     return 0;
        int count=0;
        int len=Math.min(len1,len2);
        for(int i=0;i<len;i++){
            if(s1.charAt(i)==s2.charAt(i))
                count++;
            else{
                break;
            }

        }
        return count;
    }
    public String longestCommonPrefix(String[] strs) {//最长公共前缀
        int len=strs.length;
        int left=0,right=len-1;
        if(len==1)
            return strs[0];
        int max=Integer.MAX_VALUE;
        String str="";
        while(left<right){
            int temp=TwolongestCommonPrefix(strs[left],strs[right]);
            if(temp==0)
                return "";
            if(temp<max){
                max=temp;
                str=strs[left].substring(0,max);//这个也可以放在整个while循环之外
            }
            if(strs[left].length()<strs[right].length())
                right--;
            else
                left++;
        }
        return str;

    }
}
