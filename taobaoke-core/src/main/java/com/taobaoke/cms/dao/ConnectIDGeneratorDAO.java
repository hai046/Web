package com.taobaoke.cms.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "cms")
public interface ConnectIDGeneratorDAO {
	
	@ReturnGeneratedKeys
	@SQL("insert into connect_id_generate values()")
	long generateId();

}
