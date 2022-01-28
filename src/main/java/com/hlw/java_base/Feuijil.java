package com.hlw.java_base;

import org.apache.commons.lang3.StringUtils;

public class Feuijil {
    public static void main(String[] args) {
        String str = "abdddssvbdsjljl";
        char charResult = getCharResult(str);
        System.out.println(charResult);
    }

    private static char getCharResult(String str) {
        char result = '0';
        if (StringUtils.isBlank(str)){
            return '0';
        }
        int[] hash = new int[26];
        for (char c : str.toCharArray()) {
            hash[c-'a'] += 1;
        }
        for (int i = 0; i < str.toCharArray().length; i++) {
            if (hash[str.toCharArray()[i]-'a'] ==  1){
                return str.toCharArray()[i];
            }
        }
       return  '0';
    }
}
