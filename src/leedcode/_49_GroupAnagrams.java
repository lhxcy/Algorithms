package leedcode;

import java.util.*;

/**
 * 目的：Given an array of strings, group anagrams together.
 * Created by XCY on 2017/8/14.
 * 1:正常遍历
 * 2：hashmap str为键，list为值
 * 3：定义长度为26的质数数组primes，key *= primes[i],因而速度更快
 */
public class _49_GroupAnagrams {
    public static List<List<String>> groupAnagrams1(String[] strs) {//时间复杂度太高
        List<List<String>> res = new LinkedList<List<String>>();
        int n = strs.length;
        if (strs == null || n == 0){
            return res;
        }
        boolean[] flag = new boolean[strs.length];//记录该元素是否被使用过
        for (int i = 0; i < n; i++){
            if (!flag[i]){
                List<String> list = new LinkedList<String>();
                list.add(strs[i]);
                for (int j = i + 1; j < n; j++){
                    if (!flag[j]){
                        if (isAnagrams(strs[i], strs[j])){
                            list.add(strs[j]);
                            flag[j] = true;
                        }
                    }
                }
//                System.out.println(list);
                res.add(list);
            }
        }
        return res;
    }
    public static boolean isAnagrams(String str1, String str2){
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        if (chs1.length != chs2.length){
            return false;
        }
        Arrays.sort(chs1);
        Arrays.sort(chs2);
        for (int i = 0; i < chs1.length; i++){
            if (chs1[i] != chs2[i]){
                return false;
            }
        }
        return true;
    }
    public static List<List<String>> groupAnagrams(String[] strs){
        List<List<String>> res = new LinkedList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        int n = strs.length;
        if (strs == null || n == 0){
            return res;
        }
        for (int i = 0; i < n; i++){
            char[] chs = strs[i].toCharArray();
            Arrays.sort(chs);
            String str = String.valueOf(chs);
            if (!map.containsKey(str)){
                List<String> list = new LinkedList<String>();
                list.add(strs[i]);
                map.put(str, list);
            }else {
                map.get(str).add(strs[i]);
            }
        }
        for (String key : map.keySet()){
            res.add(map.get(key));
        }
        return res;
    }

    public static List<List<String>> groupAnagrams2(String[] strs){//速度更快
        List<List<String>> res = new LinkedList<List<String>>();
        Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
        int n = strs.length;
        if (strs == null || n == 0){
            return res;
        }
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43,
                        47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103};
        for (String s : strs){
            int k = 1;
            for (char ch : s.toCharArray()){
                k *= primes[ch - 'a'];
            }
            if (!map.containsKey(k)){
                List<String> list = new LinkedList<String>();
                list.add(s);
                map.put(k, list);
            }else {
                map.get(k).add(s);
            }
        }
        for (Integer key : map.keySet()){
            res.add(map.get(key));
        }
        return res;
    }
    public static void main(String[] args){
        String[] strs = {"eat","tea","tan","ate","nat","bat"};
        System.out.println(strs.length);

        System.out.println(groupAnagrams2(strs));
    }

}
