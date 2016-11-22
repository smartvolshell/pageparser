package com.volshell.producer.customer;

import com.volshell.entity.WeChat;
import com.volshell.util.DBUtil;
import com.volshell.util.PageManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by volshell on 16-11-20.
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Producer implements Runnable {
    @Setter
    @Getter
    private Storage storage;
    @Setter
    @Getter
    private int target;

    @Override
    public void run() {
        try {
            String url = "http://www.anyv.net/index.php/viewnews-" + this.target;
            String body = PageManager.getBodyByUrl(url);
            Document document = Jsoup.parse(body);
            if (document != null && PageManager.isTarget(document)) {
                WeChat weChat = PageManager.createEntity(document);
                this.produce(weChat);
            } else {
                log.error("{} is not the target", target);
            }
        } catch (Exception e) {
            log.error("get wechat failed , error message {},error cause {}", e.getMessage(), e.getCause());
        }
    }

    private void produce(WeChat weChat) {
        storage.produce(weChat);
    }
}
