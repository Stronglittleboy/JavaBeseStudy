package com.hlw.java_base.high_concurrency.contain.test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @version 1.0.0
 * @classname: demo01
 * @author: LongWang·Hou
 * @description: 两个线程顺序打印A1B2C3D4   使用TransferQueue
 * @date: 2021/6/18 0018 11:18
 * @modified: LongWang·Hou
 */
public class demo08 {

    static char[] letter;
    static String[] number;
    static {
        letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        number = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26"};
    }
    static Thread t1 = null ,t2 = null;

    public static void main(String[] args) {
        TransferQueue<Object> queue = new LinkedTransferQueue<>();
        t2 = new Thread(() -> {
            for (String c : number) {
                try {
                    System.out.print(queue.take());
                    queue.transfer(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1 = new Thread(() -> {
                for (char c : letter) {
                    try {
                        queue.transfer(c);
                        System.out.print(queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });
        t2.start();
        t1.start();
    }
}
