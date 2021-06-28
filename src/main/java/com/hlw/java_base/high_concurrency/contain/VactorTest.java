package com.hlw.java_base.high_concurrency.contain;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @version 1.0.0
 * @classname: VactorTest
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/15 0015 15:36
 * @modified: LongWang·Hou
 */
public class VactorTest {
    private static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("第"+i+"票");
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                /*操作非原子性，超卖了*/
                while (tickets.size()>0){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("销售了" + tickets.remove(0));
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
