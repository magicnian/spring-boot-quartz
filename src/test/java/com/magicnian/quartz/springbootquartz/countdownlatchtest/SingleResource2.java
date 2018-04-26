package com.magicnian.quartz.springbootquartz.countdownlatchtest;

/**
 * Created by liunn on 2018/4/26.
 */
public class SingleResource2 {

    private static SingleResource2 singleResource2 = new SingleResource2();

    private SingleResource2(){}

    public static SingleResource2 getInstance(){
        return singleResource2;
    }
}
