package com.magicnian.quartz.springbootquartz.mqtest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费者
 * Created by liunn on 2018/2/2.
 */
public class Consumer {

    private static final String USERNAME = "magicnian";

    private static final String PASSWORD = "123456";

    private static final String BROKEN_URL = "failover://tcp://172.17.243.190:61616";

    ConnectionFactory connectionFactory;

    Connection connection;

    Session session;

    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
    AtomicInteger count = new AtomicInteger();

    public void init(){
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
            connection  = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void getMessage(String disname){
        try {
            Queue queue = session.createQueue(disname);
            MessageConsumer consumer = null;

            if(threadLocal.get()!=null){
                consumer = threadLocal.get();
            }else{
                consumer = session.createConsumer(queue);
                threadLocal.set(consumer);
            }

            //对于爬虫任务，在消费任务时需要先检查下任务是否正在运行，如果正在运行，消费该任务，但不做任何操作
            consumer.setMessageListener(message -> {
                try {
                    if(message instanceof TextMessage){
                        System.out.println(Thread.currentThread().getName()+": Consumer:我是消费者，我正在消费Msg"+((TextMessage)message).getText()+"--->"+count.getAndIncrement());
                        message.acknowledge();
                    }
                }catch (JMSException e){
                    e.printStackTrace();
                }

            });

//            while(true){
//                Thread.sleep(1000);
//                TextMessage msg = (TextMessage) consumer.receive();
//                if(msg!=null) {
//                    msg.acknowledge();
//                    System.out.println(Thread.currentThread().getName()+": Consumer:我是消费者，我正在消费Msg"+msg.getText()+"--->"+count.getAndIncrement());
//                }else {
//                    break;
//                }
//            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
