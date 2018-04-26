package com.magicnian.quartz.springbootquartz.countdownlatchtest;

/**
 * 双重检查锁的单例类
 * Created by liunn on 2018/4/26.
 */
public class SingleResource {

    private static volatile SingleResource singleResource;

    private SingleResource() {
    }


    public static SingleResource getInstance() {
        if (null == singleResource) {
            synchronized (SingleResource.class) {
                if (null == singleResource) {
                    singleResource = new SingleResource();
                }
            }
        }
        return singleResource;
    }
}
