package com.taobaoke.cms.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.Article;


@DAO(catalog="cms")
public interface ArticleDAO {
	String TABELE_NAME = "article";
	
	String INSERT_VIEW = " article_name, app_id,article_body, category_id, status, create_time";
	
	String SELECT_VIEW = " id, " + INSERT_VIEW;

	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME + " where id=:1")
	public Article getById(int id);
	
	@SQL("insert into " + TABELE_NAME + "(" + INSERT_VIEW + ") values(:1, :2, :3, :4,:5, #if(:6 == null) { now() } #else { :6 })")
	@ReturnGeneratedKeys
	public int insert(String articleName,int app_id, String articleBody, int categoryId, int status, Date now);
	
	
	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME + " where  app_id=:2  #if(:1 != -1) { and status=:1 } order by create_time desc limit :3, :4 ")
	public List<Article> getList(int status,int app_id, int offset, int limit);
	
	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME + " where 1=1 #if(:1 != -1) { and status=:1 } order by create_time desc limit :2, :3 ")
	public List<Article> getList(int status, int offset, int limit);
	
	
	@SQL("select count(1) from " + TABELE_NAME + " where 1=1 #if(:1 != -1) { and status=:1 }")
	public Integer getCount(int status);
	
	@SQL("select count(1) from " + TABELE_NAME + " where app_id=:2 #if(:1 != -1) { and status=:1 }")
	public Integer getCount(int status,int app_id);
	
	@SQL("delete from " + TABELE_NAME + " where id=:1 ")
	public int deleteById(int id);
	
	@SQL("update " + TABELE_NAME + " set status=:2 where id=:1 ")
	public int updateStatus(int id, int status);
	
	@SQL("update " + TABELE_NAME + " set app_id=:2, article_name=:3, article_body=:4, category_id=:5, status=:6 #if(:7 != null) {, create_time=:6 } where id=:1 ")
    public int update(int id,int app_id, String articleName, String articleBody, int categoryId, int status, Date now);
	
}
