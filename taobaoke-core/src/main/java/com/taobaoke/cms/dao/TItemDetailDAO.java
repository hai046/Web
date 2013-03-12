package com.taobaoke.cms.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.TItemDetail;


@DAO(catalog = "cms")
public interface TItemDetailDAO {

	String TABLE = "t_item_detail";

	String INSERT_FIELD = "detail_url, num_iid, title, nick, type, c_id, pic_url, num, valid_thru, list_time, delist_time, " + 
	        "stuff_status, location, price, post_fee, express_fee, ems_fee, has_discount, freight_payer, has_invoice, has_warranty, has_showcase," + 
	        "modified, approve_status, product_id, outer_id, is_virtual, is_taobao, is_ex, is_timing, create_time, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into "
			+ TABLE
			+ "("
			+ INSERT_FIELD
			+ ") values "
			+ "(:1.detailUrl, :1.numIid, :1.title, :1.nick, :1.type, :1.cId, :1.picUrl, :1.num, :1.validThru, :1.listTime, :1.delistTime, " + 
			":1.stuffStatus, :1.location, :1.price, :1.postFee, :1.expressFee, :1.emsFee, :1.hasDiscount, :1.freightPayer, :1.hasInvoice, :1.hasWarranty, " + 
			":1.hasShowcase, :1.modified, :1.approveStatus, :1.productId, :1.outerId, :1.isVirtual, :1.isTaobao, :1.isEx, :1.isTiming, now(), now()"
			+ " )  ON DUPLICATE KEY UPDATE detail_url=:1.detailUrl, num_iid=:1.numIid, title=:1.title, nick=:1.nick, type=:1.type, c_id=:1.cId, " 
			+ "pic_url=:1.picUrl, num=:1.num, valid_thru=:1.validThru, list_time=:1.listTime, delist_time=:1.delistTime, stuff_status=:1.stuffStatus, location=:1.location, price=:1.price, post_fee=:1.postFee, "
			+ "express_fee=:1.expressFee, ems_fee=:1.emsFee, has_discount=:1.hasDiscount, freight_payer=:1.freightPayer, has_invoice=:1.hasInvoice, has_warranty=:1.hasWarranty," 
			+ "has_showcase=:1.hasShowcase, modified=:1.modified, approve_status=:1.approveStatus, product_id=:1.productId, outer_id=:1.outerId, is_virtual=:1.isVirtual,"
			+ "is_taobao=:1.isTaobao, is_ex=:1.isEx, is_timing=:1.isTiming, update_time=now()")
	int insert(TItemDetail tItemDetail);
	

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	TItemDetail getById(int id);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where num_iid=:1")
	TItemDetail getByNumiid(long numIid);
	
	
	@SQL("delete from " + TABLE + " where id=:1")
    int deleteById(int id);

}
