package com.magicnian.quartz.springbootquartz.countdownlatchtest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 开始执行前等待n个线程完成各自任务
 * 应用程序启动类要确保在处理用户请求前，所有N个外部系统已经启动和运行
 * Created by liunn on 2018/4/26.
 */
public class CountDownLatchTest {

    private static ExecutorService executorService;

    static{
        executorService = Executors.newFixedThreadPool(10);
    }

    public static void main(String[] args) throws Exception{
        CountDownLatch latch = new CountDownLatch(10);
        for (int i=0;i<10;i++){
            Task task = new Task(i+1,latch);
            executorService.execute(task);
        }
        latch.await();
        System.out.println("sub task finish,main thread start run!");
        Thread.sleep(1000);
        executorService.shutdown();
    }

}
