package com.taobaoke.cms.model;

import java.util.Date;

public class DailyJoke {

	private  int id;
	private String content;
	private Date date;
	public int getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	public Date getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "DailyJoke [id=" + id + ", content=" + content + ", date="
				+ date + "]";
	}
	
	
}
