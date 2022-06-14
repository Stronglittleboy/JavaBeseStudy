package com.hlw.java_base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * [“eat”，“tea”，“tan”，“ate”，“nat”，“bat”]，
 */
public class Solution {
    public List<List> groupAnagrams(String[] strs) {

        //todo 补全程序
        Map<List<String>, List<String>> map = Arrays.stream(strs)
                .collect(Collectors.
                        groupingBy(item -> Arrays.stream(item.split("")).sorted()
                                .collect(Collectors.toList())));
        List<List> list = new ArrayList<>();
        map.forEach((k, v) -> list.add(v));
        return list;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] testData = new String[]{
                "eat",
                "tea",
                "tan",
                "ate",
                "nat",
                "bat"
        };
        List<List> lists = solution.groupAnagrams(testData);
        lists.forEach(System.out::println);
    }
}
