package com.hlw.java_base.designpattern.singleton;

/**
 * 双检锁
 */
public class DoubleCheck {
    private DoubleCheck() {
    }
    private static DoubleCheck instance;
    public static DoubleCheck getInstance(){
        if (null == instance) {
            synchronized (DoubleCheck.class){
                if (null == instance){
                    instance=new DoubleCheck();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args) {
        DoubleCheck instance = DoubleCheck.getInstance();
        DoubleCheck instance2 = DoubleCheck.getInstance();
        System.out.println(instance);
        System.out.println(instance2);
    }
}
