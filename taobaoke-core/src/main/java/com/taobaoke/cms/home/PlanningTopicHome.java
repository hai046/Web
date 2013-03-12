package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.PlanningTopicDAO;
import com.taobaoke.cms.model.PlanningTopic;
import com.taobaoke.cms.model.RecommendItem;

@Component
public class PlanningTopicHome {

    @Autowired
    private PlanningTopicDAO planningTopicDAO;

    private static ApplicationContext context;

    private static PlanningTopicHome instance;

    public static int TYPE_MOBILE = 1;
    public static int TYPE_WEB = 2;
    public static int ALL = -1;
    
    public static int STATUS_OK = 0;
    public static int STATUS_ERR = 1;

    public static PlanningTopicHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (PlanningTopicHome) BeanFactoryUtils.beanOfType(context, PlanningTopicHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }

    public int insert(int topicId, int weekDay) throws SQLException {
        try {
            return planningTopicDAO.insert(topicId, weekDay);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public List<Integer> getList(int offset, int limit) throws SQLException {
        try {
            return planningTopicDAO.getAll(offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public List<PlanningTopic> getAllList(int offset, int limit) throws SQLException {
        try {
            return planningTopicDAO.getAllList(offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public List<Integer> getWeekDayList(int weekDay, int offset, int limit) throws SQLException {
        try {
            return planningTopicDAO.getAll(weekDay, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public int getCount() throws SQLException {
        try {
            return planningTopicDAO.getCount();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public int del(int id) throws SQLException {
        try {
            return planningTopicDAO.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public int del(int topicId, int weekDay) throws SQLException {
        try {
            return planningTopicDAO.delete(topicId, weekDay);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public static void main(String[] args) {
        
        @SuppressWarnings("unused")
        RoseAppContext context = new RoseAppContext();
        try{
            @SuppressWarnings("unused")
            List<RecommendItem>  list = RecommendItemHome.getInstance().getAll(0, new Date(), 0, Integer.MAX_VALUE);
            System.out.println( "sfsdf" );
//            TopicItemHome.getInstance().incOrderNo(RecommendItemHome.getInstance().getMaxOrderNo(0, new Date()));
//            for(RecommendItem item : list){
//                TopicItemHome.getInstance().insert(0, item.gettItemId(), item.gettItemName(), item.gettItemPic(), item.getStatus(), item.getOrderNo());
//            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("sfsdljfkjslkdf");

    }
}
