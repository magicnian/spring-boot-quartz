package com.magicnian.quartz.springbootquartz.functiontest;

/**
 * <description>自定义一个适配器接口，用于判断S是不是T的子类</description>
 * Created by liunn on 2018/2/5.
 */
@FunctionalInterface
public interface Adapter<T, S> {

    boolean aaa(T t, S s);
}
