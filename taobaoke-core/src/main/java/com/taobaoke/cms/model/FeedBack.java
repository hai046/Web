package com.taobaoke.cms.model;

import java.util.Date;

public class FeedBack {

	private int id;
	private String mobile_model;
	private String email;
	private String content;
	private int status;
	private Date create_time, update_time ;
	public int getId() {
		return id;
	}
	public String getMobile_model() {
		return mobile_model;
	}
	public String getEmail() {
		return email;
	}
	public String getContent() {
		return content;
	}
	public int getStatus() {
		return status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMobile_model(String mobile_model) {
		this.mobile_model = mobile_model;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	
	

}
