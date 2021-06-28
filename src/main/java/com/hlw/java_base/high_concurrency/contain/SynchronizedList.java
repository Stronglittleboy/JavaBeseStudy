package com.hlw.java_base.high_concurrency.contain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @version 1.0.0
 * @classname: SynchronizedList
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/15 0015 23:13
 * @modified: LongWang·Hou
 */
public class SynchronizedList {
    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        Thread[] threads = new Thread[100];
        Random random = new Random();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int i1 = 0; i1 < 1000; i1++) {
                    list.add("a"+random.nextInt(10000));
                }
            });
        }
        runAndComputeTime(threads);
        System.out.println(list.size());
    }

    private static void runAndComputeTime(Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
