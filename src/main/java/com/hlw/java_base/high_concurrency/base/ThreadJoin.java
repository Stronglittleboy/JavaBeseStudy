package com.hlw.java_base.high_concurrency.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0.0
 * @classname: ThreadJoin
 * @author: LongWang·Hou
 * @description: 使用join完成等待排队
 * @date: 2021/6/1 0001 10:02
 * @modified: LongWang·Hou
 */
@Slf4j
public class ThreadJoin {
    public static void main(String[] args) {
        /*希望执行顺序是 A B C*/
        Thread a = new Thread(() -> log.info("我是： A"));
        Thread b = new Thread(() -> {
            try {
                a.join();
                log.info("我是： B");
            } catch (InterruptedException e) {
                log.warn("Interrupted!", e);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        });
        Thread c = new Thread(() -> {
            try {
                b.join();
                log.info("我是： c");
            } catch (InterruptedException e) {
                log.warn("Interrupted!", e);
                // Restore interrupted state...
                 Thread.currentThread().interrupt();
            }
        });
        a.start();
        c.start();
        b.start();
    }
}
