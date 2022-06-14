package com.hlw.java_base.high_concurrency.base;

import cn.hutool.core.thread.ThreadUtil;
import org.apache.commons.lang3.ThreadUtils;
import org.seleniumhq.jetty9.util.thread.QueuedThreadPool;
import org.seleniumhq.jetty9.util.thread.ThreadPool;

/**
 * <p>
 * 熟悉线程池内部结构
 *
 * @author hlw
 * @since 2021-08-05
 */
public class ThreadPoolContent {
    public static void main(String[] args) {
        ThreadUtil.newExecutor();
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool();

    }
}
