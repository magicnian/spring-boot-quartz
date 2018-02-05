package com.magicnian.quartz.springbootquartz.functiontest;

/**
 * Created by liunn on 2018/2/5.
 */
public class AdapterTest {

    public static void main(String[] args) {
        Person p = new Person();
        Student s = new Student();

        Adapter<Person, Student> adapter = (a, b) -> a.getClass().isAssignableFrom(b.getClass());
        System.out.println(adapter.aaa(p, s));

    }
}
