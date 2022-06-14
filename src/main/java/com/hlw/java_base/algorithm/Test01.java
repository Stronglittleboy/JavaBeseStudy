package com.hlw.java_base.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 如何在数组中快速找到重复次数为n的元素,例如【1,2,2,3,3,4,4,4,4,5,5,5,5,5,5】；如何找到重复次数大于等于4的元素；
 */
public class Test01 {
    public static void main(String[] args) {
        int[] request = new int[]{1,2,2,3,3,4,4,4,4,5,5,5,5,5,5};
        System.out.println(getResult(request));
    }

    /**
     * 获取结果
     *
     * @param request
     * @return
     */
    private static List<Integer> getResult(int[] request) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i : request) {
            if (null == map.get(i)){
                map.put(i,0);
            }else {
                map.put(i,map.get(i)+1);
            }
        }
        List<Integer> objects = new ArrayList<>();
        map.forEach((k,v)->{
            if (v>4){
                objects.add(k);
            }
        });
        return objects;
    }
}
