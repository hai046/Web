package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.Topic;

@DAO(catalog = "cms")
public interface TopicDAO {

	String TABLE = "topic";

	String INSERT_FIELD = "topic_name,app_id, topic_pic, topic_pad_pic, content, status, type, num_iid, url, order_no, create_time, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values "
			+ "(:1, :2, :3, :4, :5, :6, :7, :8, :9,:10, now(), now()" + " ) ")
	@ReturnGeneratedKeys
	int insert(String topicName, int app_id, String topicPic,
			String topicPadPic, String content, int status, int type,
			long numIid, String url, int orderNo);

	@SQL("insert into "
			+ TABLE
			+ "("
			+ INSERT_FIELD
			+ ") values "
			+ "(:1.topicName, :1.app_id, :1.topicPic, :1.topicPadPic, :1.content, :1.status, :1.type, :1.numIid, :1.url, :1.orderNo, now(), now()) ")
	@ReturnGeneratedKeys
	int insert(Topic topic);

	@SQL("update "
			+ TABLE
			+ " set topic_name=:1.topicName, app_id=:1.app_id, topic_pic=:1.topicPic, topic_pad_pic=:1.topicPadPic, content=:1.content, status=:1.status, type=:1.type, num_iid=:1.numIid, url=:1.url, order_no=:1.orderNo, update_time=now() where id=:1.id")
	int update(Topic topic);

	@SQL("update " + TABLE + " set status=:2 where id in (:1)")
	int setAvialable(List<Integer> topicIdList, int status);

	@SQL("update " + TABLE + " set status=:2 where id not in (:1)")
	int setUnavialable(List<Integer> topicIdList, int status);

	@SQL("update "
			+ TABLE
			+ " set topic_name=:1,app_id=:2, topic_pic=:3, topic_pad_pic=:4, content=:5, status=:6, type=:7, num_iid=:8, url=:9, order_no=:10, update_time=now() where id=:11")
	int update(String topicName,int app_id, String topicPic, String topicPadPic,
			String content, int status, int type, long numIid, String url,
			int orderNo, int id);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	Topic getById(int id);

	@SQL("select "
			+ SELECT_FIELD
			+ " from "
			+ TABLE
			+ " where 1=1 #if(:1 > -1) { and status=:1 } order by order_no limit :2, :3")
	List<Topic> getAll(int status, int offset, int limit);

	@SQL("select "
			+ SELECT_FIELD
			+ " from "
			+ TABLE
			+ " where 1=1 #if(:1 > -1) { and status=:1 and app_id=:2} order by order_no limit :3, :4")
	List<Topic> getAll(int status, int app_id, int offset, int limit);

	@SQL("select count(1) from " + TABLE
			+ " where 1=1 #if(:1 > -1) { and status=:1 } ")
	int getCount(int status);

	@SQL("update " + TABLE + " set status=:2 where id=:1")
	int updateStatus(int id, int status);

	@SQL("delete from " + TABLE + " where id=:1")
	int deleteById(int id);

}
