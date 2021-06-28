package com.hlw.java_base.high_concurrency.contain;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

/**
 * @version 1.0.0
 * @classname: HashTable
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/15 0015 13:01
 * @modified: LongWang·Hou
 */
public class HashTableTest {
    private final static int count = 1000000;
    private static UUID[] key = new UUID[count];
    private static UUID[] value = new UUID[count];
    static {
        for (int i = 0; i < count; i++) {
            key[i] = UUID.randomUUID();
            value[i] = UUID.randomUUID();
        }
    }
    private static Hashtable map  = new Hashtable<>();
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            int start = i*(count/threads.length);
            threads[i] = new Thread(()->{
                int step = count / threads.length;
                for (int i1 = start; i1 < start+step; i1++) {
                    map.put(key[i1],value[i1]);
                }
            });
        }
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(map.size());
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
