package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TItemImgDAO;
import com.taobaoke.cms.model.TItemImg;

@Component
public class TItemImgHome {

    @Autowired
    private TItemImgDAO tItemImgDAO;

    private static ApplicationContext context;

    private static TItemImgHome instance;

    public static int TYPE_MOBILE = 1;
    public static int TYPE_WEB = 2;
    public static int ALL = -1;
    
    public static int STATUS_OK = 0;
    public static int STATUS_ERR = 1;

    public static TItemImgHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (TItemImgHome) BeanFactoryUtils.beanOfType(context, TItemImgHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }

    public int insert(TItemImg tItemImg) throws SQLException {
        try {
            return tItemImgDAO.insert(tItemImg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public TItemImg getById(int id) throws SQLException {
        try {
            return tItemImgDAO.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public List<TItemImg> getListByNumiid(long numIid) throws SQLException {
        try {
            return tItemImgDAO.getLisByNumiid(numIid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public int del(int id) throws SQLException {
        try {
            return tItemImgDAO.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public static void main(String[] args) {
        RoseAppContext context = new RoseAppContext();
        TItemImgHome home = context.getBean(TItemImgHome.class);
        try {
            System.out.println("sss+++" + home.getById(1));
            System.out.println("sss+++");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sfsdljfkjslkdf");

    }
}
