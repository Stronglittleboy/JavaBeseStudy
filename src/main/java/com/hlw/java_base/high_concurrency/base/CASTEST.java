package com.hlw.java_base.high_concurrency.base;


import sun.misc.Unsafe;
/**
 * @version 1.0.0
 * @classname: CASTEST
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/3 0003 16:57
 * @modified: LongWang·Hou
 */
public class CASTEST {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        unsafe.allocateMemory(20);
    }
}
