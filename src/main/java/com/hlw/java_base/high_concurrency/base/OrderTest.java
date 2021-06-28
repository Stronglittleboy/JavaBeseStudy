package com.hlw.java_base.high_concurrency.base;

import java.util.concurrent.CountDownLatch;

/**
 * @version 1.0.0
 * @classname: OrderTest
 * @author: LongWang·Hou
 * @description: 验证乱序是真的存在的
 * @date: 2021/6/23 0023 16:22
 * @modified: LongWang·Hou
 */
public class OrderTest {
    private static int a= 0 , b= 0 ,x= 0 ,y = 0;
    public static void main(String[] args) throws InterruptedException {
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            CountDownLatch latch = new CountDownLatch(2);
            Thread thread1 = new Thread(() -> {
                a = 1;
                x = b;
                latch.countDown();
            });

            Thread thread2 = new Thread(() -> {
                b = 1;
                y = a;
                latch.countDown();
            });
            thread1.start();
            thread2.start();
            latch.await();
            String result = "第" + i + 1 + "次（" + x + "," + y + ")";
            if (0 == x && y == 0){
                System.out.println(result);
                break;
            }
        }

    }
}
