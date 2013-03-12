package com.taobaoke.cms.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.Comment;


@DAO(catalog="cms")
public interface CommentDAO {
	String TABELE_NAME = "comment";
	
	String SELECT_VIEW = " id, commentor_name, repliee_name, replied_comment_id, parent_id, source_id, type, msg, create_time";

	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME + " where id=:1")
	public Comment getById(int id);
	
	@SQL("insert into " + TABELE_NAME + "(" + SELECT_VIEW + ") values(:1, :2, :3, :4, :5, :6, :7, :8, #if(:9 == null) { now() } #else { :9 })")
	public int insert(int id, String commentorName, String replieeName, int repliedCommentId, int parentId, int sourceId, int type, String msg, Date now);
	
	
	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME + " where source_id=:1 order by create_time desc limit :2, :3 ")
	public List<Comment> getCommentListBySourceId(long sourceId, int offset, int limit);
	
	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME + " where source_id=:1 order by create_time asc limit :2, :3 ")
	public List<Comment> getCommentListBySourceIdAsc(long sourceId, int offset, int limit);
	
	
	@SQL("select count(1) from " + TABELE_NAME + " where source_id=:1 #if(:2 != -1) { and type=:2 }")
	public Integer getCommentCount(long sourceId, int type);
	
	
	@SQL("delete from " + TABELE_NAME + " where id=:1 ")
	public int deleteById(long id);
	
	@SQL("delete from " + TABELE_NAME + " where source_id=:1")
	public int deleteBySource(long sourceId);
	
	@SQL("delete from " + TABELE_NAME + " where id=:1 and commentor_uid=:2")
	public int deleteByUser(long id, int userId);
	
	@SQL("delete from " + TABELE_NAME + " where source_id=:1 and commentor_uid=:2")
	public int deleteByUserAndSource(long source_id, int userId);
	
	@SQL("select source_id from " + TABELE_NAME + " where commentor_uid=:1 #if(:2 != -1) { and type=:2 } group by source_id order by max(create_time) desc limit :3, :4 ")
	public List<Long> getUserCommentedSourceIds(int userId, int type, int offset, int limit);
	
}
