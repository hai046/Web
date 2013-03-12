package com.taobaoke.cms.home;

import java.sql.SQLException;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TItemDescDAO;

@Component
public class TItemDescHome {

    @Autowired
    private TItemDescDAO tItemDescDAO;

    private static ApplicationContext context;

    private static TItemDescHome instance;

    public static TItemDescHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (TItemDescHome) BeanFactoryUtils.beanOfType(context, TItemDescHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }

    public int insert(long numIid, String itemDesc) throws SQLException {
        try {
            return tItemDescDAO.insert(numIid, itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public String getByNumIid(long numIid) throws SQLException {
        try {
            return tItemDescDAO.getDescById(numIid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public int del(int id) throws SQLException {
        try {
            return tItemDescDAO.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public int del(long numIid) throws SQLException {
        try {
            return tItemDescDAO.deleteByNumIid(numIid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public static void main(String[] args) {
//        RoseAppContext context = new RoseAppContext();
//        TItemDescHome home = context.getBean(TItemDescHome.class);
        try {
            // System.out.println("sss+++" + home.getById(1));
            System.out.println("sss+++");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sfsdljfkjslkdf");

    }
}
