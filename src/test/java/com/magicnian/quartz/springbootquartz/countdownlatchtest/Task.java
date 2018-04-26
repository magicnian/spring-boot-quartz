package com.magicnian.quartz.springbootquartz.countdownlatchtest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by liunn on 2018/4/26.
 */
public class Task extends Thread {

    private int cost;

    private CountDownLatch latch;

    public Task(int cost,CountDownLatch latch){
        this.cost = cost;
        this.latch = latch;
    }

    public void run() {
        SingleResource singleResource = SingleResource.getInstance();
        try {
            Thread.sleep(cost*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println(Thread.currentThread().getName()+"完成任务");
    }
}
