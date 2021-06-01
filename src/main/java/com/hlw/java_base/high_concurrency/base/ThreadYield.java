package com.hlw.java_base.high_concurrency.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0.0
 * @classname: ThreadYield
 * @author: LongWang·Hou
 * @description: 线程中yield（）方法  让开Thread占用Cpu给其他线程  场景：优化线程 Debug场景  向调度程序暗示（hint）当前线程愿意让出CPU，
 * 调度程序可以忽略此暗示。也就是说当前线程可能根本就没有让出CPU，线程一直在运行。
 * @date: 2021/6/1 0001 10:12
 * @modified: LongWang·Hou
 */
@Slf4j
public class ThreadYield {
    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            log.info("测试 ======");
            Thread.yield();
            for (int i = 0; i < 10; i++) {
                log.info("测试 A");
            }

        });

        Thread b = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                log.info("测试 B");
            }
        });

        a.start();
        b.start();
    }
}
