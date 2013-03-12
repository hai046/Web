package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.annotation.AddItem2Sql;
import com.taobaoke.cms.model.TItem;


@DAO(catalog = "cms")
public interface TItemDAO {

	String TABLE = "t_item";

	String INSERT_FIELD = "num_iid, c_id, tc_id, nick, title, price, location, seller_credit_score, click_url, shop_click_url, pic_url, commission_rate, commission, " + 
	        "commission_num, commission_volume, volume, auto_send, guarantee, cash_coupon, vip_card, overseas_item, sevendays_return, real_discribe," + 
	        "onemonth_repair, cash_ondelivery, mall_item, create_time, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into "
			+ TABLE
			+ "("
			+ INSERT_FIELD
			+ ") values "
			+ "(:1.numIid, :1.cId, :1.tcId, :1.nick, :1.title, :1.price, :1.location, :1.sellerCreditScore, :1.clickUrl, :1.shopClickUrl, :1.picUrl, :1.commissionRate, :1.commission, " + 
			":1.commissionNum, :1.commissionVolume, :1.volume, :1.autoSend, :1.guarantee, :1.cashCoupon, :1.vipCard, :1.overseasItem, :1.sevendaysReturn, :1.realDiscribe, " + 
			":1.onemonthRepair, :1.cashOndelivery, :1.mallItem, now(), now()"
			+ " )  ON DUPLICATE KEY UPDATE nick=:1.nick, title=:1.title, price=:1.price, location=:1.location, seller_credit_score=:1.sellerCreditScore, click_url=:1.clickUrl, " + 
			"shop_click_url=:1.shopClickUrl, pic_url=:1.picUrl, commission_rate=:1.commissionRate, commission=:1.commission, commission_num=:1.commissionNum, " + 
			"commission_volume=:1.commissionVolume, volume=:1.volume, auto_send=:1.autoSend, guarantee=:1.guarantee, cash_coupon=:1.cashCoupon, vip_card=:1.vipCard, " + 
			"overseas_item=:1.overseasItem, sevendays_return=:1.sevendaysReturn, real_discribe=:1.realDiscribe, onemonth_repair=:1.onemonthRepair, cash_ondelivery=:1.cashOndelivery, " + 
			"mall_item=:1.mallItem, update_time=now()")
	int insert(TItem tItem);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where num_iid=:1")
	TItem getByNumId(long numIid);
	

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	TItem getById(int id);
	
//	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where status=0 #if(:1 > -1) { and tc_id=:1 } order by volume desc limit :2, :3")
//	List<TItem> getAll(int tcId, int offset, int limit);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where status=0 #if(:1 > -1) { and tc_id in (select id from t_category where parent_id=:1) }  order by :2  limit :3, :4")
    List<TItem> getAllByTCategoryParentId(int tcId, @AddItem2Sql()String orderby, int offset, int limit);
	
	@SQL("select id from " + TABLE + " where status=0 #if(:1 > -1) { and tc_id in (select id from t_category where parent_id=:1) }  order by :2  limit :3, :4")
    List<Integer> getAllIDByTCategoryParentId(int tcId, @AddItem2Sql()String orderby, int offset, int limit);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where status=0 #if(:1 > -1) { and tc_id=:1 } order by :2  limit :3, :4")
    List<TItem> getAll(int tcId, @AddItem2Sql()String orderby, int offset, int limit);
	
	@SQL("select id from " + TABLE + " where status=0 #if(:1 > -1) { and tc_id=:1 } order by :2  limit :3, :4")
    List<Integer> getAllIDList(int tcId, @AddItem2Sql()String orderby, int offset, int limit);
	
	@SQL("select count(1) from " + TABLE + " where status=0 #if(:1 > -1) { and tc_id=:1 } ")
	int getCount(int tcId);
	
	@SQL("select count(1) from " + TABLE + " where status=0 #if(:1 > -1) { and tc_id in (select id from t_category where parent_id=:1) } ")
    int getCountByTCategoryParentId(int tcId);
	
	@SQL("delete from " + TABLE + " where id=:1")
    int deleteById(int id);
	
	@SQL("update " + TABLE + " set status=:2 where id=:1")
    int updateStatus(int id, int status);

	@SQL("update " + TABLE + " set status=:2 where num_iid=:1")
    int updateStatus(long numIid, int status);
}
