package com.magicnian.quartz.springbootquartz.blockqueuetest;

/**
 * Created by liunn on 2018/4/26.
 */
public class Top3 extends Top2{

//    public Top3(String name) {
//        super(name);
//    }

    @Override
    protected void beforeDoSmth(){
        super.beforeDoSmth();
        System.out.println("top3 do smth!");
    }
}
