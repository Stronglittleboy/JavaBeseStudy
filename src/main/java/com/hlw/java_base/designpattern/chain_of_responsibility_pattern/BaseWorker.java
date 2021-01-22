package com.hlw.java_base.designpattern.chain_of_responsibility_pattern;

/**
 * @author 侯龙旺
 * @version 1.0
 * @description: TODO
 * @date 2021/1/22 16:07
 */
public abstract class BaseWorker implements Worker{
    private Worker nextWorker;

    public void addNextWorker(Worker nextWorker) {
        this.nextWorker = nextWorker;
    }
}
