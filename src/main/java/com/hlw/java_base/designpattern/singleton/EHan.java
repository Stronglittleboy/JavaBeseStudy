package com.hlw.java_base.designpattern.singleton;

/**
 * 饿汉模式
 */
public class EHan {
    private static EHan instance = new EHan();

    private EHan() {
    }
    public static EHan getInstance(){
        return instance;
    }
    public static void main(String[] args) {
        EHan instance = EHan.getInstance();
        EHan instance2 = EHan.getInstance();
        System.out.println(instance);
        System.out.println(instance2);
    }
}
