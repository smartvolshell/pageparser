package com.volshell.producer.customer;

import com.volshell.entity.WeChat;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by volshell on 16-11-20.
 */
public class Storage {
    private static int MAX_SIZE = 10;
    @Setter
    @Getter
    private List<WeChat> weChats = new LinkedList();

    public void produce(WeChat weChat) {
        synchronized (weChats) {
            //每次生產一个
            while (weChats.size() + 1 > MAX_SIZE) {
                try {
                    weChats.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            weChats.add(weChat);
            System.out.println("produce one wechat,now the size is " + weChats.size());
            weChats.notifyAll();
        }
    }

    public WeChat consume() {
        synchronized (weChats) {
            while (weChats.size() < 1) {
                try {
                    weChats.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            WeChat weChat = weChats.get(weChats.size() - 1);
            weChats.remove(weChat);
            System.out.println("consume one wechat,now the size is " + weChats.size());
            weChats.notifyAll();
            return weChat;
        }
    }

    public int size() {
        return weChats.size();
    }
}
