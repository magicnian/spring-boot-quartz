package com.magicnian.quartz.springbootquartz.blockqueuetest;

/**
 * Created by liunn on 2018/4/26.
 */
public class Top1 {

    private String name;

    public Top1(String name) {
        this.name = name;
    }

    public Top1(){
        System.out.println("top1无参构造器");
    }

    protected void beforeDoSmth() {
        System.out.println("top1 do smth!");
    }
}
