package com.hlw.java_base.designpattern.singleton;

public class Lanhan3 {
    private static Lanhan3 instance;
    /*私有构造函数*/
    private Lanhan3() {
    }
    /*返回实例 添加同步关键词*/
    public static Lanhan3 getInstance(){
        /*判断实例是否为null*/
        if (null == instance) {
            instance = new Lanhan3();
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50000; i++) {
            Runnable runnable = ()->{
                System.out.println(Lanhan3.getInstance());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}
