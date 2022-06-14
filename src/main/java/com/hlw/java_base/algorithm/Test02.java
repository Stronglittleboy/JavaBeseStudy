package com.hlw.java_base.algorithm;

import java.util.LinkedList;

/**
 * 链表翻转  头部插入
 */
public class Test02 {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>(){{
            add(5);
            add(2);
            add(6);
            add(8);
            add(20);
            add(51);
            add(1);
        }};
        System.out.println(list);
        int size = list.size();
        System.out.println(size);
        LinkedList<Integer> test = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            test.add(list.pollLast());
        }
        System.out.println(test);
    }
}
