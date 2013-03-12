package com.taobaoke.cms.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.SourceCounter;

@DAO(catalog = "cms")
public interface SourceCounterDAO {
	String TABELE_NAME = "source_counter";

	String INSERT_VIEW = " source_id, count,type,update_time";

	String SELECT_VIEW = " id , " + INSERT_VIEW;

	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME
			+ " where source_id=:1  and type=:2")
	public SourceCounter getBySource_id(int source_id, int type);

	@SQL("insert into " + TABELE_NAME + "(" + INSERT_VIEW
			+ ") values(:1, :2,:3,now()  )")
	@ReturnGeneratedKeys
	public int insert(int Source_id, int count, int type);

	@SQL("insert into " + TABELE_NAME + "(" + INSERT_VIEW
			+ ") values(:1, :2,:3,:4)  )")
	@ReturnGeneratedKeys
	public int insert(int Source_id, int count, int type, Date update_time);

	@SQL("select "+SELECT_VIEW+" from " + TABELE_NAME + " where  type=:1  limit :2,:3")
	public List<SourceCounter> getTypeSourceList(int type, int offset, int limit);

	@SQL("select `count` from " + TABELE_NAME
			+ " where  source_id=:1 and type=:2")
	public Integer getSourceCount(int source_id, int type);

	@SQL("delete from " + TABELE_NAME + " where source_id=:1 and type=:2 ")
	public int deleteBySource_id(int source_id, int type);

	@SQL("delete from " + TABELE_NAME + " where id=:1 ")
	public int deleteId(int id);

	@SQL("update "
			+ TABELE_NAME
			+ " set count=count+1 , update_time=now() where source_id=:1 and type=:2  ")
	public int updateSourceCounter(int source_id, int type);

	@SQL("update " + TABELE_NAME
			+ " set count=:2, update_time=now() where source_id=:1 ")
	public int update(int source_id, int count);
	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME
			+ " where id=:1  ")
	public SourceCounter getBy_id(int source_id);
}
