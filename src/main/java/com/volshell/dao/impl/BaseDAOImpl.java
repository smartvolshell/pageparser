package com.volshell.dao.impl;

import com.volshell.dao.BaseDAO;
import com.volshell.entity.Category;
import com.volshell.entity.WeChat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by volshell on 16-11-19.
 */
@Repository
public class BaseDAOImpl implements BaseDAO {

    private static ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();

    @Setter
    @Getter
    private SessionFactory sessionFactory;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void insertWeChat(WeChat weChat) {
        Session session = getSession();
        try {
            session.save(weChat);
        } catch (Exception e) {
            System.out.println("save wechat failed,error message  " + e.getMessage() + ",stack trace" + e.getCause().getMessage());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void insertCategory(Category category) {
        Session session = getSession();
        try {
            session.save(category);
        } catch (Exception e) {
            System.out.println("save category failed " + e.getMessage());
        }
    }

    @Override
    public boolean isExistCategory(Integer c_id) {
        Session session = this.getSession();
        Criteria criteria = session.createCriteria(Category.class);
        criteria.add(Restrictions.eq("c_id", c_id));
        return criteria.list().size() > 0 ? true : false;
    }

    private Session getSession() {
        if (sessionThreadLocal.get() == null) {
            sessionThreadLocal.set(sessionFactory.openSession());
        }
        return sessionThreadLocal.get();
    }
}
