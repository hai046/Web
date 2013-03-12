package com.taobaoke.cms.model;

import java.util.Date;

public class TItemImg {
    private long id;
    private long numIid;
    private String picUrl;
    private int position;
    private Date updateTime;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getNumIid() {
        return numIid;
    }
    public void setNumIid(long numIid) {
        this.numIid = numIid;
    }
    public String getPicUrl() {
        return picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    
}
