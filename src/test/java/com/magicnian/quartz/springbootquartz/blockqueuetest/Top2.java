package com.magicnian.quartz.springbootquartz.blockqueuetest;

/**
 * Created by liunn on 2018/4/26.
 */
public class Top2 extends Top1{

//    public Top2(String name){
//        super(name);
//    }

    @Override
    protected void beforeDoSmth(){
        super.beforeDoSmth();
        System.out.println("top2 do smth!");
    }
}
