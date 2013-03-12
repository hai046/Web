package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.TCategory;

@DAO(catalog = "cms")
public interface TCategoryDAO {

	String TABLE = "t_category";

	String INSERT_FIELD = "parent_id, name, icon_url, cid, status, type, order_no, create_time, update_time,app_id";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values "
			+ "(:1, :2, :3, :4, :5, :6, :7, now(), now()" + " ) ")
	@ReturnGeneratedKeys
	int insert(int parentId, String name, String iconUrl, String cid,
			int status, int type, int orderNo);

	@SQL("insert into "
			+ TABLE
			+ "("
			+ INSERT_FIELD
			+ ") values "
			+ "(:1.parentId, :1.name, :1.iconUrl, :1.cid, :1.status, :1.type, :1.orderNo, now(), now(),:1.app_id) ")
	@ReturnGeneratedKeys
	int insert(TCategory tCategory);

	@SQL("update "
			+ TABLE
			+ " set parent_id=:1.parentId, app_id=:1.app_id, name=:1.name, icon_url=:1.iconUrl, cid=:1.cid, status=:1.status, type=:1.type, order_no=:1.orderNo, update_time=now() where id=:1.id")
	int update(TCategory tCategory);

	@SQL("update "
			+ TABLE
			+ " set parent_id=:1, name=:2, icon_url=:3, cid=:4, status=:5, type=:6, order_no=:7, update_time=now() where id=:8")
	int update(int parentId, String name, String iconUrl, String cid,
			int status, int type, int orderNo, int id);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	TCategory getById(int id);

	@SQL("select "
			+ SELECT_FIELD
			+ " from "
			+ TABLE
			+ " where 1=1 #if(:1 > -1) { and type=:1 }  #if(:2 > -1) { and status=:2 } order by order_no limit :3, :4")
	List<TCategory> getAll(int type, int status, int offset, int limit);
	
	@SQL("select "
			+ SELECT_FIELD
			+ " from "
			+ TABLE
			+ " where 1=1 #if(:1 > -1) { and type=:1 } #if(:2>-1){ and app_id=:2 }  #if(:3 > -1) { and status=:3 } order by order_no limit :4, :5")
	List<TCategory> getAll(int type,int app_id, int status, int offset, int limit);

	@SQL("select "
			+ SELECT_FIELD
			+ " from "
			+ TABLE
			+ " where parent_id > 0 #if(:1 > -1) { and type=:1 }  #if(:2 > -1) { and status=:2 }  order by order_no limit :3, :4")
	List<TCategory> getAllSubList(int type, int status, int offset, int limit);

	@SQL("select "
			+ SELECT_FIELD
			+ " from "
			+ TABLE
			+ " where parent_id=:3 #if(:1 > -1) { and type=:1 }  #if(:2 > -1) { and status=:2 }  order by order_no limit :4, :5")
	List<TCategory> getAllSubList(int type, int status, int parentId,
			int offset, int limit);

	@SQL("select "
			+ SELECT_FIELD
			+ " from "
			+ TABLE
			+ " where parent_id = 0 #if(:1 > -1) { and type=:1 }  #if(:2 > -1) { and status=:2 } order by order_no limit :3, :4")
	List<TCategory> getAllParentList(int type, int status, int offset, int limit);

	@SQL("select "
			+ SELECT_FIELD
			+ " from "
			+ TABLE
			+ " where parent_id = 0 and app_id=:2  #if(:1 > -1) { and type=:1 }  #if(:3 > -1) { and status=:3 } order by order_no limit :4, :5")
	List<TCategory> getAllParentList(int type, int app_id, int status,
			int offset, int limit);

	@SQL("select count(1) from "
			+ TABLE
			+ " where 1=1 #if(:1 > -1) { and type=:1 }  #if(:2 > -1) { and status=:2 } ")
	int getCount(int type, int status);

	@SQL("select count(1) from "
			+ TABLE
			+ " where 1=1 #if(:1 > -1) { and type=:1 }  #if(:2>-1){ and app_id=:2 }  #if(:3 > -1) { and status=:3 } ")
	int getCount(int type, int app_id,int status);
	
	@SQL("delete from " + TABLE + " where id=:1")
	int deleteById(int id);

}
