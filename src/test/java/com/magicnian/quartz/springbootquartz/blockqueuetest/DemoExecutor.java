package com.magicnian.quartz.springbootquartz.blockqueuetest;

import java.util.concurrent.*;

/**
 * Created by liunn on 2018/4/26.
 */
public class DemoExecutor {

    public static void main(String[] args) {
        Integer threadCounter = 0;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);

        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(10, 20, 5000, TimeUnit.MILLISECONDS, blockingQueue);

        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("DemoTask Rejected : " + ((DemoThread) r).getName());
                System.out.println("Waiting for a second !!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Lets add another time : " + ((DemoThread) r).getName());
                executor.execute(r);
            }
        });

        executor.prestartAllCoreThreads();

        while (true) {
            threadCounter++;
            System.out.println("Adding DemoTask : " + threadCounter);
            executor.execute(new DemoThread(threadCounter.toString()));
            if (threadCounter == 100) {
                break;
            }
        }
    }
}
