package com.hlw.java_base;

import java.util.Arrays;

/**
 * <p>
 * 删除重复数据
 *
 * @author hlw
 * @since 2021-08-04
 */
public class TestDemo {
    public static void main(String[] args) {
        int[] date = new int[]{1, 2, 5, 5, 7, 7, 8, 8, 9};
        int n = deleteDuplicate(date);
//        int i = removeDuplicates(date);
//        System.out.println(i);
        System.out.println(n);
    }

    /**
     * 返回数组最后下标
     *
     * @param date
     * @return
     */
    private static int deleteDuplicate(int[] date) {
        int add = 0;
        for (int i = 0; i < date.length; i++) {
            if (i != date.length - 1) {
                if ((date[i] ^ date[i + 1]) != 0) {
                   date[add++] = date[i+1];
                } else {
                    date[i] = date[i+1];
                }
            }
        }
        for (int i : date) {
            System.out.print(i);
        }
        return add;
    }

    public static int removeDuplicates(int[] nums)
    {
        if (nums.length < 1){
            return nums.length;
        }
        int slow = 1;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - 1]) {
                nums[slow++] = nums[fast];
            }
        }
        return slow;
    }
}
