package com.hlw.java_base.high_concurrency.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 * @classname: SynchronizedObject
 * @author: LongWang·Hou
 * @description: 锁定对象引用变化，锁失败
 * @date: 2021/6/3 0003 16:02
 * @modified: LongWang·Hou
 */
public class SynchronizedObject {
    private static Object synchronizedObject = new SynchronizedObject();
    volatile Integer abc = 0;
    void test(){
        synchronized (synchronizedObject){
            synchronizedObject = new Object();
            for (int i = 0; i < 10000; i++) {
                abc++;
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedObject synchronizedObject = new SynchronizedObject();
        List<Thread> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(synchronizedObject::test));
        }
        list.forEach(Thread::start);
        list.forEach(item->{
            try {
                item.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(synchronizedObject.abc);
    }
}
