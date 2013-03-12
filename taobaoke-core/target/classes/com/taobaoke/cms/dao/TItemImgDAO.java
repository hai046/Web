package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.TItemImg;


@DAO(catalog = "cms")
public interface TItemImgDAO {

	String TABLE = "t_item_img";

	String INSERT_FIELD = "num_iid, pic_url, position, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert ignore into " + TABLE + "(" + INSERT_FIELD + ") values (:1.numIid, :1.picUrl, :1.position, now() )" )
	int insert(TItemImg tItem);
	

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	TItemImg getById(int id);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where num_iid=:1 order by position")
	List<TItemImg> getLisByNumiid(long numIid);
	
	@SQL("delete from " + TABLE + " where id=:1")
    int deleteById(int id);
	
	@SQL("delete from " + TABLE + " where num_iid=:1")
    int deleteByNumiid(long numIid);

}
