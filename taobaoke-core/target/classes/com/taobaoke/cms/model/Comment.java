package com.taobaoke.cms.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 评论内容 字段 {<BR>
 * id,commentorName,replieeName,repliedCommentId,articleID,type,msg,createTime<BR>
 * 
 * @author 邓海柱<br>
 *         E-mail:denghaizhu@brunjoy.com
 */
public class Comment {
	private int id;
	/**
	 * 评论者姓名
	 */
	private String commentorName;
	/**
	 * 被评论这姓名
	 */
	private String replieeName;//
	/**
	 * 评一级评论的id ，为二级评论预留
	 */
	private int repliedCommentId;//
	/**
	 * 回复一级评论里面回复的id
	 */
	private int parentId; //

	/**
	 * 帖子的ID 请用 articleID代替sourceId
	 */
	@JSONField(name = "articleID")
	private int sourceId;//

	private int type;
	private String msg;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommentorName() {
		return commentorName;
	}

	public void setCommentorName(String commentorName) {
		this.commentorName = commentorName;
	}

	public String getReplieeName() {
		return replieeName;
	}

	public void setReplieeName(String replieeName) {
		this.replieeName = replieeName;
	}

	public int getRepliedCommentId() {
		return repliedCommentId;
	}

	public void setRepliedCommentId(int repliedCommentId) {
		this.repliedCommentId = repliedCommentId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}
