package com.volshell.dao;

import com.volshell.entity.Category;
import com.volshell.entity.WeChat;

/**
 * Created by volshell on 16-11-19.
 */
public interface BaseDAO {
    void insertWeChat(WeChat weChat);

    void insertCategory(Category category);

    boolean isExistCategory(Integer c_id);
}
