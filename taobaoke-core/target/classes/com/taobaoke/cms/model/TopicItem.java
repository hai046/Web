package com.taobaoke.cms.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobaoke.cms.utils.PathUtils;

public class TopicItem {
	private int app_id;
    private int id;
    private int topicId;
    private int tItemId;
    private String tItemName;
    private String tItemPic;
    private int status;
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
    public int getTopicId() {
        return topicId;
    }
    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
    @JSONField(name="tItemId", serialize=true, deserialize=false)
    public int gettItemId() {
        return tItemId;
    }
    @JSONField(name="tItemId", serialize=false, deserialize=true)
    public void settItemId(int tItemId) {
        this.tItemId = tItemId;
    }
    @JSONField(name="tItemName", serialize=true, deserialize=false)
    public String gettItemName() {
        return tItemName;
    }
    @JSONField(name="tItemName", serialize=false, deserialize=true)
    public void settItemName(String tItemName) {
        this.tItemName = tItemName;
    }
    @JSONField(name="tItemPic", serialize=true, deserialize=false)
    public String gettItemPic() {
        return tItemPic;
    }
    public String getRealPic() {
        return PathUtils.getFileUrl(tItemPic);
    }
    @JSONField(name="tItemPic", serialize=false, deserialize=true)
    public void settItemPic(String tItemPic) {
        this.tItemPic = tItemPic;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
