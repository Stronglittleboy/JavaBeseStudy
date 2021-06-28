package com.hlw.java_base.high_concurrency.base;

import cn.hutool.core.util.StrUtil;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * @version 1.0.0
 * @classname: PhaserTest
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/7 0007 21:53
 * @modified: LongWang·Hou
 */
public class PhaserTest {


    static class MyPhaser  extends Phaser{
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
           switch (phase){
               case 0:
                   System.out.println("所有人准备到位" + registeredParties);
                   return false;
               case 1:
                   System.out.println("开始吃饭" + registeredParties);
                   return false;
               case 2:
                   System.out.println("所有人离开" + registeredParties);
                   return false;
               case 3:
                   System.out.println("送入洞房" + registeredParties);
                   return true;
               default:
                   return true;
           }
        }
    }
     static class People implements Runnable{
        private MyPhaser phaser;
        private final Random random = new Random();

        public People(MyPhaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            arrivals();
            eat();
            leave();
            hug();
        }

        private void hug() {
            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Thread.currentThread().getName().matches("新娘|新郎")){
                System.out.println("拥抱" + Thread.currentThread().getName());
               phaser.arriveAndAwaitAdvance();
            }else {
                phaser.arriveAndDeregister();
            }
        }

        private void leave() {
             try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("离开"+Thread.currentThread().getName());
             if (Thread.currentThread().getName().contains("宾客")){
                 phaser.arriveAndDeregister();
             }else {
                 phaser.arriveAndAwaitAdvance();
             }
        }

        private void eat() {
             try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("吃饭"+Thread.currentThread().getName());
             phaser.arriveAndAwaitAdvance();
        }

        private void arrivals() {
             try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("到达"+Thread.currentThread().getName());
            phaser.arriveAndAwaitAdvance();
        }
    }

    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser();
        phaser.bulkRegister(7);
        Thread[] threads = new Thread[5];
        /*初始化5个宾客*/
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new People(phaser),"宾客"+i);
            threads[i].start();
        }
        new Thread(new People(phaser),"新郎").start();
        new Thread(new People(phaser),"新娘").start();
    }
}
