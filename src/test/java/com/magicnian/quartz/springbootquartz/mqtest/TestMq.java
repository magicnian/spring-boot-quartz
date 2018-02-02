package com.magicnian.quartz.springbootquartz.mqtest;

import com.magicnian.quartz.springbootquartz.util.CommonUtil;

/**
 * Created by liunn on 2018/2/2.
 */
public class TestMq {
    public static void main(String[] args) {
        Producer producter = new Producer();
        producter.init();
        TestMq testMq = new TestMq();
        CommonUtil.sleep(1000);

        //Thread 1
        new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 2
        new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 3
        new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 4
        new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 5
        new Thread(testMq.new ProductorMq(producter)).start();

    }



    private class ProductorMq implements Runnable{
        Producer producer;
        public ProductorMq(Producer producer){
            this.producer = producer;
        }

        @Override
        public void run() {
            while(true){
                try {
                    producer.sendMessage("Jaycekon-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
