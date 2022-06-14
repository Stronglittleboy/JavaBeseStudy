package com.hlw.java_base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 *
 *
 * 输出：[2,2]
 *
 *
 * 示例 2:
 *
 *
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 *
 *
 * 输出：[4,9]
 */
public class Solution2 {
    public List computer(List num1,List num2) {
        List list = new ArrayList<>();
        for (Object s : num1) {
            if (num2.contains(s)) {
                list.add(s);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        List<Integer> num1 = Arrays.asList(4,9,5);
        List<Integer> num2 = Arrays.asList(9,4,9,8,4);

//        List<String> list = solution.groupAnagrams(num1, num2);
//        System.out.println(list);
    }
}
