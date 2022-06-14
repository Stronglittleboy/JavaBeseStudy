package com.hlw.java_base.designpattern.singleton;

public class Lanhan2 {
    private static Lanhan2 instance;
    /*私有构造函数*/
    private Lanhan2() {
    }
    /*返回实例 添加同步关键词*/
    public synchronized static Lanhan2 getInstance(){
        /*判断实例是否为null*/
        if (null == instance) {
            synchronized (DoubleCheck.class){
                instance=new DoubleCheck();
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50000; i++) {
            Runnable runnable = ()->{
                System.out.println(Lanhan2.getInstance());
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
