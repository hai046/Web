package com.taobaoke.cms.model;

import java.util.Date;

public class WebItem {
	private int id;
	private long connectId;
	private long itemId;
	
	private int type;
	private Date update_time;
	public int getId() {
		return id;
	}
	
	public long getConnectId() {
        return connectId;
    }

    public void setConnectId(long connectId) {
        this.connectId = connectId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getType() {
		return type;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
