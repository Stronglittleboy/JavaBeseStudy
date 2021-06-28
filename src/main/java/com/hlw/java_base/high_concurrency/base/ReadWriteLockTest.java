package com.hlw.java_base.high_concurrency.base;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @version 1.0.0
 * @classname: ReadWriteLockTest
 * @author: LongWang·Hou
 * @description 读写锁测试
 * @date: 2021/6/8 0008 10:54
 * @modified: LongWang·Hou
 */
public class ReadWriteLockTest {
    /*锁*/
    private static Lock lock = new ReentrantLock();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    /**
     * 读取值
     */
    public static void readValue(Lock lock){
        try {
            lock.lock();
            Thread.sleep(2000);
            System.out.println("读取完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void writeValue(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("写入完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(()->readValue(readLock)).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(()->writeValue(writeLock)).start();
        }
    }


}
