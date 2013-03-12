package com.taobaoke.cms.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.RecommendItem;


@DAO(catalog = "cms")
public interface RecommendItemDAO {

	String TABLE = "recommend_item";

	String INSERT_FIELD = "t_item_id, t_item_name, t_item_pic, status, order_no, effect_date, create_time, update_time,app_id";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into "
			+ TABLE
			+ "("
			+ INSERT_FIELD
			+ ") values "
			+ "(:1, :2, :3, :4, :5, :6, now(), now(),:7"
			+ " ) ")
	@ReturnGeneratedKeys
	int insert(int tItemId, String tItemName, String tItemPic, int status, int orderNo, Date effectDate,int app_id);
	
	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values " + "(:1.tItemId, :1.tItemName, :1.tItemPic, :1.status, :1.orderNo, :1.effectDate, now(), now(),:1.app_id) ")
    @ReturnGeneratedKeys
    int insert(RecommendItem recommendItem);
	
	@SQL("update " + TABLE + " set t_item_id=:1.tItemId, t_item_name=:1.tItemName, t_item_pic=:1.tItemPic, status=:1.status, order_no=:1.orderNo, effect_date=:1.effectDate, update_time=now() where id=:1.id")
	int update(RecommendItem recommendItem);
	
	@SQL("update " + TABLE + " set t_item_id=:1, t_item_name=:2, t_item_pic=:3, status=:4, order_no=:5, effect_date=:6, update_time=now() where id=:7")
    int update(int tItemId, String tItemName, String tItemPic, int status, int orderNo, Date effectDate, int id);
	
	@SQL("update " + TABLE + " set t_item_id=:1, t_item_name=:2, t_item_pic=:3, status=:4, order_no=:5, effect_date=:6, update_time=now(),app_id=:7 where id=:8")
    int update(int tItemId, String tItemName, String tItemPic, int status, int orderNo, Date effectDate, int app_id,int id);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	RecommendItem getById(int id);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where 1=1 #if(:1 > -1) { and status=:1 } #if(:2 != null) { and effect_date=:2 } order by order_no limit :3, :4")
	List<RecommendItem> getAll(int status, Date effectDate, int offset, int limit);
	
	@SQL("select MAX(order_no) from " + TABLE + " where 1=1 #if(:1 > -1) { and status=:1 } #if(:2 != null) { and effect_date=:2 } ")
    Integer getMaxOrderNo(int status, Date effectDate);
	
	@SQL("select count(1) from " + TABLE + " where 1=1 #if(:1 > -1) { and status=:1 } #if(:2 != null) { and effect_date=:2 }")
	int getCount(int status, Date effectDate);
	
	@SQL("update " + TABLE + " set status=:2 where id=:1")
    int updateStatus(int id, int status);
	
	@SQL("delete from " + TABLE + " where id=:1")
    int deleteById(int id);

}
