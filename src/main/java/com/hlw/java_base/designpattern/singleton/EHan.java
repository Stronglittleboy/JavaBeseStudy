package com.hlw.java_base.designpattern.singleton;

/**
 * 饿汉模式
 */
public class EHan {
    /*静态new独享*/
    private static EHan instance = new EHan();
    /*私有构造参数*/
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
