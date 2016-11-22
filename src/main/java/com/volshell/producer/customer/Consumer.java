package com.volshell.producer.customer;

import com.volshell.entity.WeChat;
import com.volshell.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by volshell on 16-11-20.
 */
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Consumer implements Runnable {
    @Setter
    @Getter
    private Storage storage;
    @Setter
    @Getter
    private BaseService baseService;

    @Override
    public void run() {
        baseService.saveWechat(this.consume());
    }

    private WeChat consume() {
        return storage.consume();
    }
}
