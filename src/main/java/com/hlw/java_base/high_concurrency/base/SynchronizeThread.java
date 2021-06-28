package com.hlw.java_base.high_concurrency.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0.0
 * @classname: SynchronizeThread
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/3 0003 17:45
 * @modified: LongWang·Hou
 */
public class SynchronizeThread {
    volatile Integer abc = 0;
    private static final SynchronizeThread t = new SynchronizeThread();
    synchronized void add(){
        new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

    synchronized void start(){
        System.out.println("开始试试");
    }

    public static void main(String[] args) {
        new Thread(t::add).start();
        new Thread(t::start).start();
    }
}
