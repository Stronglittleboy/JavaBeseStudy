package com.hlw.java_base.high_concurrency.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @version 1.0.0
 * @classname: CountDownLatchTest
 * @author: LongWang·Hou
 * @description: CountDownLatch 模拟并发 秒杀抢购环境
 * @date: 2021/6/4 0004 10:19
 * @modified: LongWang·Hou
 */
public class CountDownLatchTest2 {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Thread(()->{
                /*阻塞，等待发令枪*/
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前的线程名称："+Thread.currentThread().getName());
                System.out.println("开始启动");
            }));
        }
        list.forEach(Thread::start);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*开始开枪，比赛开始*/
        countDownLatch.countDown();
    }
}
