package com.hlw.java_base.designpattern.singleton;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Lanhan {
    private static Lanhan instance;
    /*私有构造函数*/
    private Lanhan() {
    }
    /*返回实例*/
    public static Lanhan getInstance(){
        /*判断实例是否为null*/
        if (null == instance) {
            instance = new Lanhan();
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50000; i++) {
            Runnable runnable = ()->{
                System.out.println(Lanhan.getInstance());
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
