package com.taobaoke.cms.home;

import java.sql.SQLException;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TItemDetailDAO;
import com.taobaoke.cms.model.TItemDetail;

@Component
public class TItemDetailHome {

    @Autowired
    private TItemDetailDAO tItemDetailDAO;

    private static ApplicationContext context;

    private static TItemDetailHome instance;

    public static int TYPE_MOBILE = 1;
    public static int TYPE_WEB = 2;
    public static int ALL = -1;
    
    public static int STATUS_OK = 0;
    public static int STATUS_ERR = 1;

    public static TItemDetailHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (TItemDetailHome) BeanFactoryUtils.beanOfType(context, TItemDetailHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }

    public int insert(TItemDetail tItemDetail) throws SQLException {
        try {
            return tItemDetailDAO.insert(tItemDetail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public TItemDetail getById(int id) throws SQLException {
        try {
            return tItemDetailDAO.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public TItemDetail getByNumiid(long numIid) throws SQLException {
        try {
            return tItemDetailDAO.getByNumiid(numIid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public int del(int id) throws SQLException {
        try {
            return tItemDetailDAO.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public static void main(String[] args) {
        RoseAppContext context = new RoseAppContext();
        TItemDetailHome home = context.getBean(TItemDetailHome.class);
        try {
            System.out.println("sss+++" + home.getById(1));
            System.out.println("sss+++");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sfsdljfkjslkdf");

    }
}
