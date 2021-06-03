package com.hlw.java_base.high_concurrency.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0.0
 * @classname: Atom_AtomicInteger
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/3 0003 16:24
 * @modified: LongWang·Hou
 */
public class Atom_AtomicInteger {
    volatile AtomicInteger abc = new AtomicInteger(0);
    void add(){
        for (int i = 0; i < 10000; i++) {
         abc.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        List<Thread> list = new ArrayList();
        Atom_AtomicInteger t = new Atom_AtomicInteger();
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
