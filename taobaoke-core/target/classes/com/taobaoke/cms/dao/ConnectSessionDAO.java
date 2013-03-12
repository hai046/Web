package com.taobaoke.cms.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "cms")
public interface ConnectSessionDAO {

	String TABLE = "connect_session";

	String INSERT_FIELD = "connect_id, session_id, create_time, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values " + 
	"(:1, :2, now(), now()) ON DUPLICATE KEY UPDATE session_id=:2, update_time=now()")
	int insert(long connectId, String sessionId);
	
	@SQL("select session_id from " + TABLE + " where connect_id=:1")
    String getSessionId(long connectId);

	@SQL("delete from " + TABLE + "  where session_id=:1")
	public int del(String sessionId);

}
