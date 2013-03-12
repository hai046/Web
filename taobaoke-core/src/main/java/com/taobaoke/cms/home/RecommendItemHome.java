package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.RecommendItemDAO;
import com.taobaoke.cms.model.RecommendItem;

@Component
public class RecommendItemHome {

    @Autowired
    private RecommendItemDAO recommendItemDAO;

    private static ApplicationContext context;

    private static RecommendItemHome instance;

    public static int STATUS_OK = 0;
    public static int STATUS_NO_DISPLAY = 1;

    public static RecommendItemHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (RecommendItemHome) BeanFactoryUtils.beanOfType(context, RecommendItemHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }

    public List<RecommendItem> getAll(int status, Date effectDate, int offset, int limit) throws SQLException {
        try {
            if( effectDate != null ){
                effectDate = DateUtils.setHours(effectDate, 0);
                effectDate = DateUtils.setMinutes(effectDate, 0);
                effectDate = DateUtils.setSeconds(effectDate, 0);
            }
            return recommendItemDAO.getAll(status, effectDate, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public Integer getMaxOrderNo(int status, Date effectDate) throws SQLException{
        Integer result = 0;
        try {
            if( effectDate != null ){
                effectDate = DateUtils.setHours(effectDate, 0);
                effectDate = DateUtils.setMinutes(effectDate, 0);
                effectDate = DateUtils.setSeconds(effectDate, 0);
            }
            result = recommendItemDAO.getMaxOrderNo(status, effectDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
        return result == null ? 0 : result;
    }
    
    
    public int getCount(int status, Date effectDate) throws SQLException {
        try {
            if( effectDate != null ){
                effectDate = DateUtils.setHours(effectDate, 0);
                effectDate = DateUtils.setMinutes(effectDate, 0);
                effectDate = DateUtils.setSeconds(effectDate, 0);
            }
            return recommendItemDAO.getCount(status, effectDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public int update(RecommendItem recommendItem) throws SQLException {
        try {
            return recommendItemDAO.update(recommendItem);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public int update(int tItemId, String tItemName, String tItemPic, int status, int orderNo, Date effectiveDate, int id) throws SQLException {
        try {
            return recommendItemDAO.update(tItemId, tItemName, tItemPic, status, orderNo, effectiveDate, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public RecommendItem getById(int id) throws SQLException {
        try {
            return recommendItemDAO.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public int insert(RecommendItem topicItem) throws SQLException {
        try {
            return recommendItemDAO.insert(topicItem);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public int insert(int tItemId, String tItemName, String tItemPic, int status, int orderNo, Date effectiveDate,int app_id) throws SQLException {
        try {
            return recommendItemDAO.insert(tItemId, tItemName, tItemPic, status, orderNo, effectiveDate,app_id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }
    
    public int del(int id) throws SQLException {
        try {
            return recommendItemDAO.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        RoseAppContext context = new RoseAppContext();
        try {
            Calendar calendar = Calendar.getInstance();
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if( weekDay == 0 ){
                weekDay = 7;
            }
//            List<Integer> topicIdList = PlanningTopicHome.getInstance().getWeekDayList(weekDay, 0, Integer.MAX_VALUE);
            List<Integer> topicIdList = new ArrayList<Integer>();
            topicIdList.add(1);topicIdList.add(2);//topicIdList.add(3);
//            TopicHome.getInstance().setAvialable(topicIdList);
            TopicHome.getInstance().setUnavialable(topicIdList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sfsdljfkjslkdf");

    }
}
