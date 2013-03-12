package com.taobaoke.cms.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "cms")
public interface TItemPcDAO {

	String TABLE = "t_item_pc";

	String INSERT_FIELD = "num_iid, click_url, create_time, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values " + 
	"(:1, :2, now(), now()) ON DUPLICATE KEY UPDATE click_url=:2, update_time=now()")
	int insert(long numIid, String clickUrl);
	
	@SQL("select click_url from " + TABLE + " where num_iid=:1")
    String getClickUrl(long numIid);

	@SQL("delete from " + TABLE + "  where num_iid=:1")
	public int del(long numIid);

}
