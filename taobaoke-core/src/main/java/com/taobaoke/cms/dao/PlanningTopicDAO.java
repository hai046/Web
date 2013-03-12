package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.PlanningTopic;


@DAO(catalog = "cms")
public interface PlanningTopicDAO {

	String TABLE = "planning_topic";

	String INSERT_FIELD = "topic_id, week_day, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into "
			+ TABLE
			+ "("
			+ INSERT_FIELD
			+ ") values "
			+ "(:1, :2, now()"
			+ " ) ")
	@ReturnGeneratedKeys
	int insert(int topicId, int weekDay);
	
	@SQL("select topic_id from " + TABLE + " limit :1, :2")
	List<Integer> getAll(int offset, int limit);
	
	@SQL("select topic_id from " + TABLE + " where week_day=:1 limit :2, :3")
    List<Integer> getAll(int weekDay, int offset, int limit);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " limit :1, :2")
    List<PlanningTopic> getAllList(int offset, int limit);
	
	@SQL("select count(1) from " + TABLE)
	int getCount();
	
	@SQL("delete from " + TABLE + " where id=:1")
    int deleteById(int id);
	
	@SQL("delete from " + TABLE + " where id=:1")
    int delete(int topicId, int weekDay);

}
