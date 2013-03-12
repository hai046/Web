package com.taobaoke.cms.model;

import java.util.Date;

public class SourceStore {
	private int id;
	private String source_key,value;
	private int type;
	private Date update_time;
	public int getId() {
		return id;
	}
	public String getSource_key() {
		return source_key;
	}
	public String getValue() {
		return value;
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
	public void setSource_key(String source_key) {
		this.source_key = source_key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
