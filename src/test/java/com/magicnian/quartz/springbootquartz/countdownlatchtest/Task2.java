package com.magicnian.quartz.springbootquartz.countdownlatchtest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by liunn on 2018/4/26.
 */
public class Task2 extends Thread {

    private CountDownLatch latch;

    public Task2(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            latch.await();
            SingleResource singleResource = SingleResource.getInstance();
            System.out.println(Thread.currentThread().getName() + " : " + singleResource);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
