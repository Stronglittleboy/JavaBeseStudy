package com.hlw.java_base.high_concurrency.base;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @version 1.0.0
 * @classname: CyclicbarrierTest
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/4 0004 11:07
 * @modified: LongWang·Hou
 */
public class CyclicbarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("到达餐厅"));

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i]= new Thread(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "到达餐厅");
                /*阻塞等待人数够了，然后进餐*/
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "线程开始进餐");
            });
        }
        Arrays.stream(threads).forEach(Thread::start);
    }
}
