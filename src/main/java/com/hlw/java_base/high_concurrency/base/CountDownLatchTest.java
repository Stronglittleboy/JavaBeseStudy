package com.hlw.java_base.high_concurrency.base;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @classname: CountDownLatchTest
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/4 0004 10:19
 * @modified: LongWang·Hou
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        CountDownLatch countDownLatch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                System.out.println("测试");
                countDownLatch.countDown();

            });
        }

        Arrays.stream(threads).forEach(Thread::start);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("最后执行的结果");
    }
}
