package com.hlw.java_base.high_concurrency.contain;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 * @classname: List
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/15 0015 15:26
 * @modified: LongWang·Hou
 */
public class ListTest {
    private static List<String> tickets = new ArrayList<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("第"+i+"票");
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                while (tickets.size()>0){
                    System.out.println("销售了" + tickets.remove(0));
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
