package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;


@DAO(catalog = "cms")
public interface WebItemDAO {
	String TABELE_NAME = "web_item";

	String INSERT_VIEW = " device_no, item_id, type, update_time";

	String SELECT_VIEW = " id , " + INSERT_VIEW;


	@SQL("insert into " + TABELE_NAME + "(" + INSERT_VIEW
			+ ") values(:1, :2,:3, now()) ON DUPLICATE KEY UPDATE update_time=now()")
	public int insert(String deviceNo, long itemId, int type);


	@SQL("select item_id from " + TABELE_NAME + " where device_no=:1 and type=:2 order by update_time desc limit :3,:4")
	public List<Long> getItemIdList(String deviceNo, int type, int offset, int limit);
	
	@SQL("select count(1) from " + TABELE_NAME + " where device_no=:1 and type=:2")
    public Integer getCount(String deviceNo, int type);
	
	@SQL("select id from " + TABELE_NAME + " where device_no=:1 and item_id=:2 and type=:3")
    public Integer get(String deviceNo, long itemId, int type);

	@SQL("delete from " + TABELE_NAME + " where device_no=:1 and item_id=:2 and type=:3")
	public int deleteByItemId(String deviceNo, long itemId, int type);

	@SQL("delete from " + TABELE_NAME + " where id=:1 ")
	public int deleteId(int id);
}
