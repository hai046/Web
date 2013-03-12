package com.taobaoke.cms.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.taobaoke.cms.utils.PathUtils;

public class RecommendItem {
	private int app_id;
    private int id;
    private int tItemId;
    private String tItemName;
    private String tItemPic;
    private int status;
    private int orderNo;
    private Date effectDate;
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
    public int gettItemId() {
        return tItemId;
    }
    public void settItemId(int tItemId) {
        this.tItemId = tItemId;
    }
    public String gettItemName() {
        return tItemName;
    }
    public void settItemName(String tItemName) {
        this.tItemName = tItemName;
    }
    public String gettItemPic() {
        return tItemPic;
    }
    public String getRealPic() {
        return PathUtils.getFileUrl(tItemPic);
    }
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
    public Date getEffectDate() {
        return effectDate;
    }
    public String getEffectDateStr() {
        if( effectDate == null ){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = "";
        try{
            result = format.format(effectDate);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
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
