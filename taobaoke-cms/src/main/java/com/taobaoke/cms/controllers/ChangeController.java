package com.taobaoke.cms.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.crawlers.CacheFacade;
import com.taobaoke.cms.home.PlanningTopicHome;
import com.taobaoke.cms.home.RecommendItemHome;
import com.taobaoke.cms.home.TopicHome;
import com.taobaoke.cms.home.TopicItemHome;
import com.taobaoke.cms.model.RecommendItem;
import com.taobaoke.cms.redis.RedisPoolFactory;

@Component
@Path("/change")
public class ChangeController {
    @Autowired
    TopicHome topicHome;
    
    @Autowired
    TopicItemHome topicItemHome;
    
    @Autowired
    RecommendItemHome recommendItemHome;
    
    @Autowired
    PlanningTopicHome planningTopicHome;
    
    CacheFacade cacheFacade = new CacheFacade();
    
    
    @Get("/topic")
    public String topic(Invocation inv) {
        try {
            Calendar calendar = Calendar.getInstance();
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if( weekDay == 0 ){
                weekDay = 7;
            }
            List<Integer> topicIdList = planningTopicHome.getWeekDayList(weekDay, 0, Integer.MAX_VALUE);
            TopicHome.getInstance().setUnavialable(topicIdList);
            TopicHome.getInstance().setAvialable(topicIdList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "@OK";
    }
    
    @Get("/item")
    public String item(Invocation inv) {
        try{
            List<RecommendItem>  list = recommendItemHome.getAll(RecommendItemHome.STATUS_OK, new Date(), 0, Integer.MAX_VALUE);
            List<Integer> tItemIdList = new ArrayList<Integer>();
            for( RecommendItem item : list ){
                tItemIdList.add(item.gettItemId());
            }
            
            int orderNoIncrement = recommendItemHome.getMaxOrderNo(RecommendItemHome.STATUS_OK, new Date())+1;
            int topicItemMinOderNo = topicItemHome.getMinOrderNo(0, TopicItemHome.STATUS_OK, tItemIdList);
            if( topicItemMinOderNo < orderNoIncrement ){
                topicItemHome.incOrderNo(orderNoIncrement);
            }
            for(RecommendItem item : list){
                topicItemHome.insert(0, item.gettItemId(), item.gettItemName(), item.gettItemPic(), item.getStatus(), item.getOrderNo(),item.getApp_id());
                recommendItemHome.del(item.getId());
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            RedisPoolFactory.delete("topic_item_" + 0);
            RedisPoolFactory.delete("topic_item_count_" + 0);
        }
        return "@OK";
    }
    
    
    @Get("/cache")
    public String cache(Invocation inv) {
        try{
            cacheFacade.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "@OK";
    }
}
