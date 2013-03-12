package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.FeedBack;

@DAO(catalog = "cms")
public interface FeedBackDAO {

	String TABLE = "feedback";

	String INSERT_FIELD = "mobile_model,email,content,status,create_time,update_time ";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into "
			+ TABLE
			+ "("
			+ INSERT_FIELD
			+ ") values "
			+ "(:1.mobile_model, :1.email, :1.content, :1.status, now(), now())")
	int insert(FeedBack feedBack);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	FeedBack getById(int id);

	@SQL("select " + SELECT_FIELD + " from " + TABLE
			+ " where  status=:1  order by id desc limit :2, :3")
	List<FeedBack> getAllByStatus(int status, int offset, int limit);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + "  order by id desc  limit :1, :2 ")
	List<FeedBack> getAll(int offset, int limit);

	@SQL("select count(1) from " + TABLE)
	int getCount();

	@SQL("select count(1) from " + TABLE + "  where status=:1")
	int getCountByStatus(int status);

	@SQL("delete from " + TABLE + " where id=:1")
	int deleteById(int id);

	@SQL("update " + TABLE + " set status=:1  where id=:2")
	public int updateStatus(int status, int id);
}
