package com.volshell.core;

import com.volshell.parser.constant.Constants;
import com.volshell.producer.customer.Consumer;
import com.volshell.producer.customer.Producer;
import com.volshell.producer.customer.Storage;
import com.volshell.service.BaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by volshell on 16-11-20.
 */
public class Client {
    static Consumer consumer;


    public static void main(String[] args) {

        Long start = System.currentTimeMillis();
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-hibernate.xml");
        consumer = (Consumer) context.getBean("consumer");
        ExecutorService producerService = Executors.newFixedThreadPool(5);
        ExecutorService consumerService = Executors.newFixedThreadPool(2);

        Storage storage = new Storage();


        int key = 66000;
        int end = Constants.LARGEST_NUMBER;
        if (args != null && args.length > 0) {
            key = Integer.parseInt(args[0]);
            end = Integer.parseInt(args[1]);
        }
        while (key <= end) {
            Producer producer = new Producer();
            producer.setStorage(storage);
            consumer.setStorage(storage);
            producer.setTarget(key);
            producerService.execute(producer);
            consumerService.execute(consumer);
            key++;
        }
        if (key == end) {
            producerService.shutdown();
            consumerService.shutdown();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("spend time is " + (endTime - start));

    }
}
