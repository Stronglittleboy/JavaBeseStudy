package com.hlw.java_base.high_concurrency.base;

import javax.naming.Context;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0.0
 * @classname: Wifi
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/9 0009 10:35
 * @modified: LongWang·Hou
 */
public class Wifi {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);


        lock.lock();


        lock.unlock();
    }
}
