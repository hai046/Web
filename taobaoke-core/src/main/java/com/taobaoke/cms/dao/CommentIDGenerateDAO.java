package com.taobaoke.cms.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "cms")
public interface CommentIDGenerateDAO {
	
	@ReturnGeneratedKeys
	@SQL("insert into comment_id_generate values()")
	int generateId();

}
