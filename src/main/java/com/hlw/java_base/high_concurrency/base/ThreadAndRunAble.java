package com.hlw.java_base.high_concurrency.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0.0
 * @classname: ThreadAndRunAble
 * @author: LongWang·Hou
 * @description: 创建线程的两种方式 ： Thread和Runable 还有lambda表达式（）->{}  还有线程池Excuse执行
 * @date: 2021/5/31 0031 17:36
 * @modified: LongWang·Hou
 */
@Slf4j
public class ThreadAndRunAble {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        new Thread(() -> {
            for (int s = 0; s < 20; s++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.warn("Interrupted!", e);
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
                log.info("测试进度");
            }
        }).start();
    }
}


/**
 * @version 1.0.0
 * @classname: MyThread
 * @author: LongWang·Hou
 * @description: 我的私有线程
 * @date: 2021/5/31 0031 17:39
 * @modified: LongWang·Hou
 */
@Slf4j
class MyThread extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                Thread.sleep(20);
                log.info("执行我的线程");
            }
        } catch (Exception e) {
            log.warn("Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }
}
