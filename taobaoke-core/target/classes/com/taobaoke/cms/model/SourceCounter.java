package com.taobaoke.cms.model;

import java.util.Date;

public class SourceCounter {
	private int source_id,id,type;
	private Date update_time;
	private long count;
	
	
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public int getSource_id() {
		return source_id;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	
	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setType(int type) {
		this.type = type;
	}

	

	
	
}
