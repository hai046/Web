package com.taobaoke.cms.model;

import java.util.Date;

public class Article {
	private int id,app_id;
	private String articleName;
	private String articleBody;
	private int categoryId;
	private int status;
	private Date createTime;
	private int replyNum;
	
	
	public int getApp_id() {
		return app_id;
	}
	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    public String getArticleName() {
        return articleName;
    }
    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }
    public String getArticleBody() {
        return articleBody;
    }
    public void setArticleBody(String articleBody) {
        this.articleBody = articleBody;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
	
}
