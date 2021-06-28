package com.hlw.java_base.high_concurrency.contain;

import sun.misc.Queue;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @version 1.0.0
 * @classname: QueueTest
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/15 0015 15:41
 * @modified: LongWang·Hou
 */
public class QueueTest {
    private static ConcurrentLinkedQueue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("第"+i+"票");
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                while (true) {
                    String poll = tickets.poll();
                    if (null == poll){
                        break;
                    }
                    System.out.println("销售了" + poll);
                }

            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
