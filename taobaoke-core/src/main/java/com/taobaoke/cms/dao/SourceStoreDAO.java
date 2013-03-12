package com.taobaoke.cms.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.SourceStore;


@DAO(catalog = "cms")
public interface SourceStoreDAO {
	String TABELE_NAME = "source_store";

	String INSERT_VIEW = " source_key, `value`,type,update_time";

	String SELECT_VIEW = " id , " + INSERT_VIEW;

	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME
			+ " where source_key=:1  and type=:2")
	public SourceStore getBySource_key(String source_key, int type);

	@SQL("insert into " + TABELE_NAME + "(" + INSERT_VIEW
			+ ") values(:1, :2,:3,now()  )")
	@ReturnGeneratedKeys
	public int insert(String source_key, String value, int type);

	@SQL("insert into " + TABELE_NAME + "(" + INSERT_VIEW
			+ ") values(:1, :2,:3,:4)  )")
	@ReturnGeneratedKeys
	public int insert(String source_key, String value, int type, Date update_time);

	@SQL("select "+SELECT_VIEW+" from " + TABELE_NAME + " where  type=:1  limit :2,:3")
	public List<SourceStore> getTypeSourceList(int type, int offset, int limit);

	@SQL("select `value` from " + TABELE_NAME
			+ " where  source_key=:1 and type=:2")
	public String getSourceValue(String source_key, int type);

	@SQL("delete from " + TABELE_NAME + " where source_key=:1 and type=:2 ")
	public int deleteBysource_key(String source_key, int type);

	@SQL("delete from " + TABELE_NAME + " where id=:1 ")
	public int deleteId(int id);

	@SQL("select  count(id) from " + TABELE_NAME )
	public int getCount();
	@SQL("select  count(id) from " + TABELE_NAME + " where type=:1 ")
	public int getCount(int type);

	@SQL("update " + TABELE_NAME
			+ " set `value`=:3, update_time=now() where source_key=:1 and type=:2 ")
	public int update(String source_key, int type ,String value);
	@SQL("select " + SELECT_VIEW + " from " + TABELE_NAME
			+ " where id=:1  ")
	public SourceStore getBy_id(int id);
}
