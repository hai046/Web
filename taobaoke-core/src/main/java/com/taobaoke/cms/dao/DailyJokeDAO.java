package com.taobaoke.cms.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.DailyJoke;

@DAO(catalog = "cms")
public interface DailyJokeDAO {

	String TABLE = "daily_joke";

	String INSERT_FIELD = "content,date";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values "
			+ "(:1.content, :1.date)")
	int insert(DailyJoke mDailyJoke);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	DailyJoke getById(int id);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where `date`=:1")
	DailyJoke getByDate(Date date);

	@SQL("select " + SELECT_FIELD + " from " + TABLE
			+ " where  `date`<=:1  order by `date` desc limit :2, :3")
	List<DailyJoke> getAll(Date date, int offset, int limit);

	@SQL("select " + SELECT_FIELD + " from " + TABLE
			+ "  where  `date`<=now()  order by `date` desc  limit :1, :2")
	List<DailyJoke> getAllBeforeNow(int offset, int limit);

	@SQL("select " + SELECT_FIELD + " from " + TABLE
			+ "  order by `date` desc  limit :1, :2")
	List<DailyJoke> getAll(int offset, int limit);

	@SQL("select count(1) from " + TABLE)
	int getCount();

	@SQL("select count(1) from " + TABLE + "  where  `date`<=now() ")
	int getCountBefore();

	@SQL("delete from " + TABLE + " where `date`<=:1")
	int deleteBefore(Date date);

	@SQL("select `date` from " + TABLE + "  order by `date` desc limit 1")
	Date getLastDate();
	
	@SQL("select `date` from " + TABLE + "  order by `date` asc limit 1")
	Date getBeforeDate();

	@SQL("update " + TABLE + " set content=:1  where id=:2")
	public int update(String content, int id);

	@SQL("update " + TABLE + " set content=:1  where `date`=:2")
	public int update(String content, Date date);

}
