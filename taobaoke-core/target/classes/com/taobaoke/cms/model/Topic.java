package com.taobaoke.cms.model;

import java.util.Date;

import com.taobaoke.cms.utils.PathUtils;

public class Topic {
	private int app_id;
    private int id;
    private String topicName;
    private String topicPic;
    private String topicPadPic;
    private String content;
    private int status;
    private int type;
    private long numIid;
    private String url;
    private int orderNo;
    private Date createTime;
    private Date updateTime;
    
    
    
    public int getApp_id() {
		return app_id;
	}
	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTopicName() {
        return topicName;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    public String getTopicPic() {
        return topicPic;
    }
    
    public String getRealTopicPic() {
        return PathUtils.getFileUrl(topicPic);
    }
    
    public String getRealTopicPadPic() {
        return PathUtils.getFileUrl(topicPadPic);
    }
    
    public void setTopicPic(String topicPic) {
        this.topicPic = topicPic;
    }
    
    public String getTopicPadPic() {
        return topicPadPic;
    }
    public void setTopicPadPic(String topicPadPic) {
        this.topicPadPic = topicPadPic;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getNumIid() {
        return numIid;
    }
    public void setNumIid(long numIid) {
        this.numIid = numIid;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
