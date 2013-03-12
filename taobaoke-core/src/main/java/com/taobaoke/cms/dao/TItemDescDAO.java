package com.taobaoke.cms.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;


@DAO(catalog = "cms")
public interface TItemDescDAO {

	String TABLE = "t_item_desc";

	String INSERT_FIELD = "num_iid, item_desc, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values (:1, :2, now() )  ON DUPLICATE KEY UPDATE item_desc=:2, update_time=now()")
	int insert(long numIid, String itemDesc);
	

	@SQL("select item_desc from " + TABLE + " where num_iid=:1")
	String getDescById(long numIid);
	
	@SQL("delete from " + TABLE + " where id=:1")
    int deleteById(int id);

	@SQL("delete from " + TABLE + " where num_iid=:1")
    int deleteByNumIid(long numIid);
}
