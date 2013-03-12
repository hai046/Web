package com.taobaoke.cms.model;

import java.util.Date;

public class PlanningTopic {
    private int id;
    private int topicId;
    private int weekDay;
    private Date updateTime;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTopicId() {
        return topicId;
    }
    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
    public int getWeekDay() {
        return weekDay;
    }
    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
}
