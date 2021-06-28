package com.hlw.java_base.high_concurrency.contain.test;

import sun.misc.ConditionLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0.0
 * @classname: demo01
 * @author: LongWang·Hou
 * @description: 两个线程顺序打印A1B2C3D4   使用LockSupport
 * @date: 2021/6/18 0018 11:18
 * @modified: LongWang·Hou
 */
public class demo03 {

    static char[] letter;
    static String[] number;

    static {
        letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        number = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26"};
    }

    static Thread t1 = null ,t2 = null;

    static volatile Boolean condition = true;
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition lock1 = lock.newCondition();
        t2 = new Thread(() -> {
            lock.lock();
            for (String c : number) {
                while (condition){
                    try {
                        lock1.signal();
                        lock1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(c);
                try {
                    lock1.signal();
                    lock1.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        });
        t1 = new Thread(() -> {
                condition=false;
                lock.lock();
                for (char c : letter) {
                    System.out.print(c);
                    try {
                        lock1.signal();
                        lock1.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            lock.unlock();
        });


        t2.start();
        t1.start();
    }
}
