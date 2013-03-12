package com.taobaoke.cms.crawlers;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.taobaoke.cms.home.TCategoryHome;
import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.home.TopicHome;
import com.taobaoke.cms.home.TopicItemHome;
import com.taobaoke.cms.model.TCategory;
import com.taobaoke.cms.model.Topic;

public class SingleCachInitialer extends Thread {

    // TItemHome tItemHome

    CacheFacade facade = null;
    public SingleCachInitialer(CacheFacade facade) {
        this.facade = facade;
    }

    @Override
    public void run() {
        try {
            TCategoryHome home = TCategoryHome.getInstance();
            List<TCategory> list = home.getAllCacheFacdeParentList(TCategoryHome.TYPE_MOBILE, TCategoryHome.STATUS_OK, 0, Integer.MAX_VALUE);
            for (TCategory tc : list) {
                TItemHome.getInstance().getAllByTCategoryParentId(tc.getId(), "volume", true, 0, Integer.MAX_VALUE);
                List<TCategory> subList = home.getAllSubList(TCategoryHome.TYPE_MOBILE, TCategoryHome.STATUS_OK, tc.getId(), 0, Integer.MAX_VALUE);
                for (TCategory subTc : subList) {
                    TItemHome.getInstance().getAllByTCategoryParentId(subTc.getId(), "volume", true, 0, Integer.MAX_VALUE);
                }
            }
            List<Topic> topicList = TopicHome.getInstance().getAll(TopicHome.STATUS_OK, 0, Integer.MAX_VALUE);
            TopicItemHome.getInstance().getAll(0, TopicHome.STATUS_OK, 0, Integer.MAX_VALUE);
            for( Topic topic : topicList ){
                TopicItemHome.getInstance().getAll(topic.getId(), TopicHome.STATUS_OK, 0, Integer.MAX_VALUE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            facade.callMeWhenFinished();
        }
    }

    public static void main(String[] args) {
        String[] cids = "50012832, 50024156".split(",");
        for (String temp : cids) {

            System.out.println("aa==" + NumberUtils.toLong(temp));
        }

    }
}
