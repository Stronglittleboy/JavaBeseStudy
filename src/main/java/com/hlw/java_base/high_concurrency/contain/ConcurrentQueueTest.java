package com.hlw.java_base.high_concurrency.contain;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @version 1.0.0
 * @classname: ConcurrentQueueTest
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/15 0015 23:18
 * @modified: LongWang·Hou
 */
public class ConcurrentQueueTest {
    public static void main(String[] args) {
        Queue<Object> queue = new ConcurrentLinkedQueue<>();
    }
}
