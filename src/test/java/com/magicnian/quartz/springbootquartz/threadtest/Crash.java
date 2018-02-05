package com.magicnian.quartz.springbootquartz.threadtest;

import com.magicnian.quartz.springbootquartz.util.CommonUtil;

/**
 * Threadlocal学习
 * Created by liunn on 2018/2/5.
 */
public class Crash {

    static class Number {

        static ThreadLocal<Integer> threadLocal = null;

        public Number(Integer value) {
            threadLocal = ThreadLocal.withInitial(() -> value);//jdk8新增的初始化方法
        }

        public void increase() {
            threadLocal.set(threadLocal.get() + 10);
            CommonUtil.sleep(10);
            System.out.println(Thread.currentThread().getName()+" increase value: " + threadLocal.get());
        }

        public void decrease() {
            threadLocal.set(threadLocal.get() - 10);
            CommonUtil.sleep(100);
            System.out.println(Thread.currentThread().getName()+" decrease value: " + threadLocal.get());
        }
    }

    public static void main(String[] args) {
        Number number = new Number(5);
        new Thread(number::increase).start();//第一个线程从threadLocal中拷贝一份副本
        new Thread(number::decrease).start();//第二个线程从threadLocal中拷贝一份副本，和第一个线程中的副本是独立的两份
    }
}
