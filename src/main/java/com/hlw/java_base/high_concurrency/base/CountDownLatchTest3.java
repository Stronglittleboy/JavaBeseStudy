package com.hlw.java_base.high_concurrency.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0.0
 * @classname: CountDownLatchTest
 * @author: LongWang·Hou
 * @description: CountDownLatch 让单个线程等待：多个线程(任务)完成后，进行汇总合并
 * @date: 2021/6/4 0004 10:19
 * @modified: LongWang·Hou
 */
public class CountDownLatchTest3 {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(20);
        AtomicInteger integer = new AtomicInteger(0);
        List<Thread> list = new ArrayList<>();
//        int randNumber =rand.nextInt(MAX - MIN + 1) + MIN;
        for (int i = 0; i < 20; i++) {
            list.add(new Thread(()->{
                try {
                    Random random = new Random();
                    int i1 = random.nextInt(100) + 1;
                    Thread.sleep(i1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                integer.incrementAndGet();
                /*阻塞，等待发令枪*/
                countDownLatch.countDown();
                System.out.println("当前的线程名称："+Thread.currentThread().getName());
                System.out.println("开始启动");
            }));
        }
        list.forEach(Thread::start);

        /*阻塞，主分支等待次分支结果*/
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*预估结果是20*/
        System.out.println(integer.get());
    }
}
