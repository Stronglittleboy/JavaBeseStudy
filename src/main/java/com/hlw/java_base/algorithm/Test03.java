package com.hlw.java_base.algorithm;

import cn.hutool.core.collection.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *
 * @author hlw
 * @date 2021/8/4 0004 17:10
 * @desc 计算第k大数
 */
public class Test03 {


    public static void main(String[] args) {
        /*测试数据*/
        int[] data = new int[]{5,6,7,8,10,4,6,241,52,1};
        /*预期结果*/
        int expectedResults = 7;
        /*计算第6个大数*/
        int result = compute(data);
        System.out.println(result == expectedResults);
    }

    /**
     * 获取计算结果
     *
     * @param data
     * @return
     */
    private static int compute(int[] data) {
        for (int datum : data) {
            for (int i : data) {
                
            }
        }
        return 0;
    }
}
