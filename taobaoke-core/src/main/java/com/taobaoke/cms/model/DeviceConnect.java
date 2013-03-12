package com.taobaoke.cms.model;

import java.util.Date;

public class DeviceConnect {

	private  int id;
	private String deviceNo;
	private long connectId;
	private int status;
	private Date createTime;
	private Date updateTime;
	public int getId() {
		return id;
	}
    public String getDeviceNo() {
        return deviceNo;
    }
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
    public long getConnectId() {
        return connectId;
    }
    public void setConnectId(long connectId) {
        this.connectId = connectId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
    public void setId(int id) {
        this.id = id;
    }
	
}
