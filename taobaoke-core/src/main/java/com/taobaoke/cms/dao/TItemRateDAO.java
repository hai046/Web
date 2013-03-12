package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.TItemRate;

@DAO(catalog = "cms")
public interface TItemRateDAO {

	String TABLE = "t_item_rate";

	String INSERT_FIELD = "num_iid, annoy, buyer, credit, comment_date, deal, rate_id, content, type, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert ignore into " + TABLE + "(" + INSERT_FIELD + ") values (:1.numIid, :1.annoy, :1.buyer, :1.credit, :1.commentDate, :1.deal, :1.rateId, :1.content, :1.type, now() )" )
	int insert(TItemRate tItemRate);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	TItemRate getById(int id);

	@SQL("select " + SELECT_FIELD + " from " + TABLE
			+ " where num_iid=:1 order by rate_id desc limit :2, :3")
	List<TItemRate> getLisByNumiid(long numIid, int offset, int limit);

	@SQL("select count(num_iid) from " + TABLE + " where num_iid=:1")
	int getCountByNumIid(long numIid);

	@SQL("delete from " + TABLE + " where id=:1")
	int deleteById(int id);

	@SQL("delete from " + TABLE + " where num_iid=:1")
	int deleteByNumiid(long numIid);

}
