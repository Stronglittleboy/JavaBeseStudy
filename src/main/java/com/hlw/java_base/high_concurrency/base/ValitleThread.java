package com.hlw.java_base.high_concurrency.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 * @classname: ValitleThread
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/3 0003 15:48
 * @modified: LongWang·Hou
 */
public class ValitleThread {
    volatile Integer abc = 0;
    void add(){
        for (int i = 0; i < 10000; i++) {
//            synchronized (ValitleThread.class){
                abc++;
//            }
        }
    }

    public static void main(String[] args) {
        List<Thread> list = new ArrayList();
        ValitleThread t = new ValitleThread();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(t::add));
        }
        list.forEach(Thread::start);
        list.forEach(item->{
            try {
                item.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.abc);
    }
}
