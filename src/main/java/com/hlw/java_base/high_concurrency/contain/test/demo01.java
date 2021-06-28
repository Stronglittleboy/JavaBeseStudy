package com.hlw.java_base.high_concurrency.contain.test;

/**
 * @version 1.0.0
 * @classname: demo01
 * @author: LongWang·Hou
 * @description: 两个线程顺序打印A1B2C3D4
 * @date: 2021/6/18 0018 11:18
 * @modified: LongWang·Hou
 */
public class demo01 {

    static char[] letter;
    static String[] number;

    static {
        letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        number = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26"};
    }

    private static Object object = new Object();
    /*保证执行顺序  线程内存可见数据  门栓  直接awit() */
        private static volatile Boolean switchOpen = true;
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (object) {
//                try {
//                    object.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                for (String c : number) {
                    System.out.print(c);
                    try {
                        object.notify();
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();
            }
        }).start();
        new Thread(() -> {
            synchronized (object) {
                for (char c : letter) {
                    System.out.print(c);
                    switchOpen = true;
                    try {
                        object.notify();
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();
            }

        }).start();

    }
}
