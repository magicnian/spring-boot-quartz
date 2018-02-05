package com.magicnian.quartz.springbootquartz.functiontest;

/**
 * 自定义一个适配器接口，用于判断
 * @param S
 * 是不是
 * @param T
 * 的子类
 * Created by liunn on 2018/2/5.
 */
@FunctionalInterface
public interface Adapter<T, S> {

    boolean aaa(T t, S s);
}
