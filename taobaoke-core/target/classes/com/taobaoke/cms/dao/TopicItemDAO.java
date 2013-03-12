package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.TopicItem;


@DAO(catalog = "cms")
public interface TopicItemDAO {

	String TABLE = "topic_item";

	String INSERT_FIELD = "topic_id, t_item_id, t_item_name, t_item_pic, status, order_no, create_time, update_time,app_id";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into "
			+ TABLE
			+ "("
			+ INSERT_FIELD
			+ ") values "
			+ "(:1, :2, :3, :4, :5, :6, now(), now(),:7"
			+ " ) ON DUPLICATE KEY UPDATE topic_id=:1, t_item_id=:2, t_item_name=:3, t_item_pic=:4, status=:5, order_no=:6, update_time=now(),app_id=:7 ")
	int insert(int topicId, int tItemId, String tItemName, String tItemPic, int status, int orderNo,int app_id);
	
	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values " + "(:1.topicId, :1.tItemId, :1.tItemName, :1.tItemPic, :1.status, :1.orderNo, now(), now(),:1.app_id) ")
    @ReturnGeneratedKeys
    int insert(TopicItem topicItem);
	
	@SQL("update " + TABLE + " set topic_id=:1.topicId, t_item_id=:1.tItemId, t_item_name=:1.tItemName, t_item_pic=:1.tItemPic, status=:1.status, order_no=:1.orderNo, update_time=now() where id=:1.id")
	int update(TopicItem topicItem);
	
	@SQL("update " + TABLE + " set topic_id=:1, t_item_id=:2, t_item_name=:3, t_item_pic=:4, status=:5, order_no=:6, update_time=now() where id=:7")
    int update(int topicId, int tItemId, String tItemName, String tItemPic, int status, int orderNo, int id);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	TopicItem getById(int id);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where topic_id=:1 #if(:2 > -1) { and status=:2 } order by order_no limit :3, :4")
	List<TopicItem> getAll(int topicId, int status, int offset, int limit);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where topic_id=:1 #if(:2 > -1) { and status=:3 and app_id=:2 } order by order_no limit :4, :5")
	List<TopicItem> getAll(int topicId,int app_id, int status, int offset, int limit);
	
	@SQL("update " + TABLE + " set order_no=order_no+:1")
    int incOrderNo(int increment);
	
	@SQL("select MIN(order_no) from " + TABLE + " where topic_id=:1 #if(:2 > -1) { and status=:2 } #if(:3 != null) { and t_item_id not in(:3) } ")
    int getMinOrderNo(int topicId, int status, List<Integer> tItemIdList);
	
	@SQL("select count(1) from " + TABLE + " where topic_id=:1 #if(:2 > -1) { and status=:2 } ")
	int getCount(int topicId, int status);
	
	@SQL("update " + TABLE + " set status=:2 where id=:1")
    int updateStatus(int id, int status);
	
	@SQL("delete from " + TABLE + " where id=:1")
    int deleteById(int id);

}
