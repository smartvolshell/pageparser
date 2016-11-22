package com.volshell.service;

import com.volshell.dao.BaseDAO;
import com.volshell.entity.Category;
import com.volshell.entity.WeChat;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by volshell on 16-11-20.
 */
@Service
@Transactional
public class BaseService {
    @Setter
    private BaseDAO baseDAO;

    public void saveWechat(WeChat weChat) {
        if (weChat == null) {
            return;
        }
        Category category = weChat.getCategory();
        if (category != null && !baseDAO.isExistCategory(category.getC_id())) {

            baseDAO.insertCategory(category);
        }
        baseDAO.insertWeChat(weChat);
    }
}
