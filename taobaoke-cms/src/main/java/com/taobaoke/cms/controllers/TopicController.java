package com.taobaoke.cms.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.taobao.api.internal.util.StringUtils;
import com.taobaoke.cms.fileupload.FileSystemBCS;
import com.taobaoke.cms.home.PlanningTopicHome;
import com.taobaoke.cms.home.RecommendItemHome;
import com.taobaoke.cms.home.TopicHome;
import com.taobaoke.cms.home.TopicItemHome;
import com.taobaoke.cms.model.PlanningTopic;
import com.taobaoke.cms.model.RecommendItem;
import com.taobaoke.cms.model.Topic;
import com.taobaoke.cms.model.TopicItem;
import com.taobaoke.cms.utils.CookieUtil;

@Component
@Path("/topic")
public class TopicController {
    @Autowired
    TopicHome topicHome;
    
    @Autowired
    TopicItemHome topicItemHome;
    
    @Autowired
    RecommendItemHome recommendItemHome;
    
    @Autowired
    PlanningTopicHome planningTopicHome;
    
    
    private static final int DEFAULT_SIZE = 20;
    
    @Get("/topicList")
    public String topicList(Invocation inv, @Param("currentPage") int page) {
        page = page < 1 ? 1 : page;
        int offset = (page - 1) * DEFAULT_SIZE;
        
        try {
            List<Topic> topicList =null;
            int count =0;
            if(CookieUtil.getApp_id()<0)
            {
            	topicList= topicHome.getAll(TopicHome.STATUS_ALL, offset, DEFAULT_SIZE);
            	count = topicHome.getCount(TopicHome.STATUS_ALL,-1);
            }else
            {
            	topicList= topicHome.getAll(TopicHome.STATUS_ALL, CookieUtil.getApp_id(),offset, DEFAULT_SIZE);
            	count = topicHome.getCount(TopicHome.STATUS_ALL,CookieUtil.getApp_id());
            }
            
            long pageCount = count / (long) DEFAULT_SIZE;
            pageCount = pageCount * DEFAULT_SIZE == count ? pageCount : pageCount + 1;
            
            inv.addModel("topicList", topicList);
            inv.addModel("count",  count);
            inv.addModel("pageCount", pageCount);
            inv.addModel("currentPage", page);
            inv.addModel("callBackForNotSameRoot", URLEncoder.encode("/topic/topicList?currentPage="+page, "utf-8"));
            inv.addModel("callBack", URLEncoder.encode("./topicList?currentPage="+page, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "topic/topicList";
    }
    
    @Get("/topicAdd")
    public String showSingle(Invocation inv, @Param("backUrl") String backUrl, @Param("id") int id) {
        try{
            Topic topic = topicHome.getById(id);
            inv.addModel("topic", topic);
        }catch(Exception e){
            e.printStackTrace();
        }
        inv.addModel("backUrl", backUrl);
        return "topic/single";
    }
    
    @Post("/topicAdd")
    public String topicAdd(Invocation inv, Topic topic, @Param("backUrl") String backUrl, @Param("iconFile") MultipartFile photo, @Param("iconFilePad") MultipartFile padPhoto) {
        int topicId = 0;
        String picPath = "";
        String padPicPath = "";
        try {
            if( photo != null && photo.getBytes().length > 0 ){
                picPath = FileSystemBCS.getInstance().storeFile(photo.getBytes(), photo.getName(), photo.getContentType());
            }
            if( padPhoto != null && padPhoto.getBytes().length > 0 ){
                padPicPath = FileSystemBCS.getInstance().storeFile(padPhoto.getBytes(), padPhoto.getName(), padPhoto.getContentType());
            }
            topic.setTopicPic(picPath);
            topic.setTopicPadPic(padPicPath);
            if( topic.getId() > 0 ){
                topicId = topic.getId();
                Topic originTopic = topicHome.getById(topic.getId());
                if( StringUtils.isEmpty(picPath) ){
                    topic.setTopicPic( originTopic.getTopicPic() );
                }
                if( StringUtils.isEmpty(padPicPath) ){
                    topic.setTopicPadPic( originTopic.getTopicPadPic() );
                }
                topicHome.update(topic);
            }else{
                topicId = topicHome.insert(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "r:./topicAdd?id="+topicId+"&backUrl="+backUrl;
    }
    
    @Post("/changeTopicStatus")
    public String changeTopicStatus(Invocation inv, @Param("id") int id, @Param("currentPage") int currentPage) {
        try {
            Topic topic = topicHome.getById(id);
            if( topic != null ){
                topicHome.updateStatus(id, Math.abs(topic.getStatus() - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "r:./topicList?currentPage="+currentPage;
    }
    
    
    
    @Get("/itemList")
    public String itemList(Invocation inv, @Param("topicId") int topicId, @Param("currentPage") int page, @Param("backUrl") String backUrl) {
        page = page < 1 ? 1 : page;
        int offset = (page - 1) * DEFAULT_SIZE;
        
        try {
            List<TopicItem> topicItemList = topicItemHome.getAll(topicId, TopicItemHome.STATUS_OK, offset, DEFAULT_SIZE);
            int count = topicItemHome.getCount(topicId, TopicItemHome.STATUS_OK);
            
            
            long pageCount = count / (long) DEFAULT_SIZE;
            pageCount = pageCount * DEFAULT_SIZE == count ? pageCount : pageCount + 1;
            
            inv.addModel("topicItemList", topicItemList);
            inv.addModel("count",  count);
            inv.addModel("pageCount", pageCount);
            inv.addModel("currentPage", page);
            inv.addModel("topicId", topicId);
            inv.addModel("backUrl", backUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "topic/itemList";
    }
    
    @Post("/addItem")
    public String addItem(Invocation inv, TopicItem topicItem, @Param("currentPage") int page, @Param("backUrl") String backUrl, @Param("pic") MultipartFile photo) {
        int topicId= topicItem.getTopicId();
        try {
            String picPath = "";
            if( photo != null && photo.getBytes().length > 0 ){
                picPath = FileSystemBCS.getInstance().storeFile(photo.getBytes(), photo.getName(), photo.getContentType());
            }
            topicItem.settItemPic(picPath);
            topicItemHome.insert(topicItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inv.addModel("callBack", backUrl);
        return "r:./itemList?topicId="+topicId+"&currentPage="+page+"&backUrl="+backUrl;
    }
    
    @Post("/itemDel")
    public String itemDel(Invocation inv, @Param("id") int id, @Param("topicId") int topicId, @Param("backUrl") String backUrl, @Param("currentPage") int page) {
        try {
            topicItemHome.del(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inv.addModel("callBack", backUrl);
        return "r:./itemList?topicId="+topicId+"&currentPage="+page+"&backUrl="+backUrl;
    }
    
    
    
    @Get("/recommendList")
    public String recommendList(Invocation inv, @Param("currentPage") int page, @Param("backUrl") String backUrl, @Param("effectDate") Date effectDate) {
        page = page < 1 ? 1 : page;
        int offset = (page - 1) * DEFAULT_SIZE;
        
        try {
            List<RecommendItem> recommendList = recommendItemHome.getAll(RecommendItemHome.STATUS_OK, effectDate, offset, DEFAULT_SIZE);
            int count = recommendItemHome.getCount(TopicItemHome.STATUS_OK, effectDate);
            
            
            long pageCount = count / (long) DEFAULT_SIZE;
            pageCount = pageCount * DEFAULT_SIZE == count ? pageCount : pageCount + 1;
            
            inv.addModel("recommendList", recommendList);
            inv.addModel("count",  count);
            inv.addModel("pageCount", pageCount);
            inv.addModel("currentPage", page);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            inv.addModel("effectDate", sdf.format(effectDate));
            inv.addModel("backUrl", backUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "topic/recommend";
    }
    
    @Post("/addRecommend")
    public String addRecommend(Invocation inv, RecommendItem recommendItem, @Param("currentPage") int page, @Param("backUrl") String backUrl, @Param("pic") MultipartFile photo) {
        try {
            String picPath = "";
            if( photo != null && photo.getBytes().length > 0 ){
                picPath = FileSystemBCS.getInstance().storeFile(photo.getBytes(), photo.getName(), photo.getContentType());
            }
            recommendItem.settItemPic(picPath);
            recommendItemHome.insert(recommendItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inv.addModel("callBack", backUrl);
        try {
            return "r:./recommendList?currentPage="+page+"&backUrl="+backUrl+"&effectDate="+URLEncoder.encode(recommendItem.getEffectDateStr(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "r:./recommendList?currentPage="+page+"&backUrl="+backUrl;
    }
    
    @Post("/recommendDel")
    public String recommendDel(Invocation inv, @Param("id") int id, @Param("backUrl") String backUrl, @Param("currentPage") int page, @Param("effectDate") String effectDate) {
        try {
            recommendItemHome.del(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inv.addModel("callBack", backUrl);
        return "r:./recommendList?currentPage="+page+"&backUrl="+backUrl+"&effectDate="+effectDate;
    }
    
    @Get("/planningTopicList")
    public String planningTopicList(Invocation inv, @Param("currentPage") int page, @Param("backUrl") String backUrl) {
        page = page < 1 ? 1 : page;
        int offset = (page - 1) * DEFAULT_SIZE;
        
        try {
            List<PlanningTopic> planningTopicList = planningTopicHome.getAllList(offset, DEFAULT_SIZE);
            int count = planningTopicHome.getCount();
            
            
            long pageCount = count / (long) DEFAULT_SIZE;
            pageCount = pageCount * DEFAULT_SIZE == count ? pageCount : pageCount + 1;
            
            inv.addModel("planningTopicList", planningTopicList);
            inv.addModel("count",  count);
            inv.addModel("pageCount", pageCount);
            inv.addModel("currentPage", page);
            inv.addModel("backUrl", backUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "topic/planningTopicList";
    }
    
    @Post("/addPlanningTopic")
    public String addPlanningTopic(Invocation inv, @Param("currentPage") int page, @Param("backUrl") String backUrl, @Param("topicId") int topicId, @Param("weekDay") int weekDay) {
        try {
            planningTopicHome.insert(topicId, weekDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inv.addModel("callBack", backUrl);
        
        return "r:./planningTopicList?currentPage="+page+"&backUrl="+backUrl;
    }
    
    @Post("/delPlanningTopic")
    public String delPlanningTopic(Invocation inv, @Param("id") int id, @Param("backUrl") String backUrl, @Param("currentPage") int page, @Param("effectDate") String effectDate) {
        try {
            planningTopicHome.del(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inv.addModel("callBack", backUrl);
        return "r:./planningTopicList?currentPage="+page+"&backUrl="+backUrl;
    }
    
}
