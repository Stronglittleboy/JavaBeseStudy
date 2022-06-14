package com.hlw.java_base.high_concurrency.base;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @version 1.0.0
 * @classname: RandomTest
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/7/14 0014 15:00
 * @modified: LongWang·Hou
 */
public class RandomTest {
    public static void main(String[] args) {
//        randomSigle();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }

    private static void randomSigle() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }
}
