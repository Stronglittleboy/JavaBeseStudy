package com.hlw.java_base.high_concurrency.contain.test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @classname: demo01
 * @author: LongWang·Hou
 * @description: 两个线程顺序打印A1B2C3D4   自定义CAS方法
 * @date: 2021/6/18 0018 11:18
 * @modified: LongWang·Hou
 */
public class demo06 {

    static char[] letter;
    static String[] number;
    static {
        letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        number = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26"};
    }
    static ArrayBlockingQueue queue1 = new ArrayBlockingQueue(1);
    static ArrayBlockingQueue queue2 = new ArrayBlockingQueue(1);
    static Thread t1 = null ,t2 = null;

    public static void main(String[] args) {

        t2 = new Thread(() -> {
            try {
                queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String c : number) {
                System.out.print(c);
                try {
                    queue2.put("1");
                    queue1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1 = new Thread(() -> {
                for (char c : letter) {
                    System.out.print(c);
                    try {
                        queue1.put("1");
                        queue2.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });
        t2.start();
        t1.start();
    }
}
