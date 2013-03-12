package com.taobaoke.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.taobaoke.cms.model.TItem;
import com.taobaoke.cms.model.TItemDetail;
import com.taobaoke.cms.utils.PriceFormat;

public class JSonFiledPutUtil {
	public static JSONObject getTaobokeItemObject(TItem item) {
		if (item == null)
			return null;
		JSONObject obj = new JSONObject();
		obj.put("num_iid", item.getNumIid());
		obj.put("id", item.getId());
		obj.put("cid", item.getcId());
		obj.put("click_url", item.getClickUrl());
		obj.put("volume", item.getVolume());
		obj.put("item_location", (item.getLocation()));
		obj.put("nick", (item.getNick()));
		obj.put("pic_url", item.getPicUrl());
		obj.put("price", PriceFormat.formatPrice(item.getPrice()));
		obj.put("title", (item.getTitle()));
		obj.put("shop_click_url", item.getShopClickUrl());
		obj.put("seller_credit_score", item.getSellerCreditScore());
		obj.put("cash_ondelivery", item.getCashOndelivery());
		return obj;

	}

	/**
	 * 不包含 desc 和 imgs属性
	 * 
	 * @param item
	 * @return
	 */
	public static JSONObject getTaobaokeDetailObject(TItemDetail item) {
		JSONObject obj = new JSONObject();
		obj.put("id", item.getId());
		obj.put("numIid", item.getNumIid());
		obj.put("picUrl", item.getPicUrl());
		obj.put("cId", item.getcId());
		obj.put("price", PriceFormat.formatPrice(item.getPrice()));
		obj.put("title", item.getTitle());
		obj.put("num", item.getNum());
		obj.put("location", item.getLocation());
		obj.put("detailUrl", item.getDetailUrl());

		obj.put("postFee", item.getPostFee());
		obj.put("expressFee", item.getExpressFee());
		obj.put("emsFee", item.getEmsFee());
		obj.put("hasDiscount", item.getHasDiscount());
		obj.put("freightPayer", item.getFreightPayer());

		obj.put("hasInvoice", item.getHasInvoice());
		obj.put("hasWarranty", item.getHasWarranty());
		obj.put("hasShowcase", item.getHasShowcase());

		obj.put("productId", item.getProductId());
		obj.put("outerId", item.getOuterId());

		obj.put("isVirtual", item.getIsVirtual());
		obj.put("type", item.getType());
		obj.put("nick", item.getNick());

		return obj;
	}

}
