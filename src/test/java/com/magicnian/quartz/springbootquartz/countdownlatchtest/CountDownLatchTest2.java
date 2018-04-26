package com.magicnian.quartz.springbootquartz.countdownlatchtest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用countdownlatch实现最大的并行性
 * 创建一个初始计数为1的CountDownLatch，并让所有线程都在这个锁上等待
 * 只需调用 一次countDown()方法就可以让所有的等待线程同时恢复执行
 * 可以用于测试单例类
 * Created by liunn on 2018/4/26.
 */
public class CountDownLatchTest2 {

    private static ExecutorService executorService;

    static {
        executorService = Executors.newFixedThreadPool(10);
    }

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            Task2 task2 = new Task2(latch);
            executorService.execute(task2);
        }

        Thread.sleep(1000);

        latch.countDown();
        Thread.sleep(1000);
        executorService.shutdown();
    }
}
