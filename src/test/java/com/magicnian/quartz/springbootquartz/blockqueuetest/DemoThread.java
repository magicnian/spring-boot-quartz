package com.magicnian.quartz.springbootquartz.blockqueuetest;

/**
 * Created by liunn on 2018/4/26.
 */
public class DemoThread implements Runnable{

    private String name;

    public DemoThread(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Executing : " + name);
    }
}
