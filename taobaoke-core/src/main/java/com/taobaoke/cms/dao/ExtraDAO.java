package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.ExtraItem;

@DAO(catalog = "cms")
public interface ExtraDAO {

	String TABLE = "extra_table";

	String INSERT_FIELD = " `key`,`value`,name,`type` ";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values "
			+ "(:1.key, :1.value, :1.name,:1.type)")
	int insert(ExtraItem mExtraItem);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	ExtraItem getById(int id);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where `key`=:1")
	ExtraItem getByKey(String key);

	
	@SQL("select value from " + TABLE + " where `key`=:1")
	String getValueByKey(String key);
	
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " order by `type`  limit :1, :2")
	List<ExtraItem> getAll(int offset, int limit);

	
	
	@SQL("select count(1) from " + TABLE)
	int getCount();

	@SQL("delete from " + TABLE + " where id=:1")
	int deleteById(int id);

	@SQL("delete from " + TABLE + " where `key`=:1")
	int deleteByKey(String key);
	@SQL("update  " + TABLE + " set `name`=:2,`value`=:3 where `id`=:1")
	int updateById(int id, String name, String value);

}
