package com.taobaoke.cms.crawlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.home.TItemRateHome;
import com.taobaoke.cms.model.TItemRate;
import com.taobaoke.cms.utils.HttpUtils;

public class ItemCommentCrawler {
	public static final String COMMENT_BASE_URL = "http://a.m.taobao.com/ajax/rate_list.do?item_id=%d&rateRs=1&p=%d&ps=100";

	Log log =LogFactory.getLog(getClass());
	public List<TItemRate> start(long numIid, int pageNo) throws Exception {
		
		String url = String.format(COMMENT_BASE_URL, numIid, pageNo);
		String test = HttpUtils.getFileContentFromUrl(url);
		JSONObject json;
		try {
			json = JSON.parseObject(test);
		} catch (Exception e) {
			log.error("此商品可能已经下架   numIid="+numIid);
			TItemHome.getInstance().setUnuseableByNumIid(numIid);
			throw new Exception("该宝贝已下架，去看看别的吧");//可以在api断拦截  实现err_msg
		}
		if(!json.containsKey("items"))
		{
			log.error("暂时没有该商品评论   numIid="+numIid);
//			throw new Exception("该宝贝还没有评论");
		}
		List<TItemRate> lists = new ArrayList<TItemRate>();
		for (Object o : json.getJSONArray("items")) {
//			System.out.println(o);
			if (!(o instanceof JSONObject)) {
				continue;
			}
			TItemRate tItemRate = changeJSON2TItemRate((JSONObject) o);
			tItemRate.setNumIid(numIid);
			lists.add(tItemRate);
			TItemRateHome.getInstance().insert(tItemRate);
		}
		return lists;
	}


	private TItemRate changeJSON2TItemRate(JSONObject json) {
		TItemRate tItemRate = new TItemRate();
		tItemRate.setAnnoy(json.getIntValue("annoy"));
		tItemRate.setBuyer(json.getString("buyer"));
		tItemRate.setCredit(json.getIntValue("credit"));
		tItemRate.setCommentDate(json.getDate("date"));
		tItemRate.setDeal(json.getString("deal"));
		tItemRate.setRateId(json.getLongValue("rateId"));
		tItemRate.setContent(json.getString("text"));
		tItemRate.setType(json.getIntValue("type"));
		
		return tItemRate;
	}

	public static void main(String[] args) {
		// RoseAppContext context = new RoseAppContext();
		// ItemCommentCrawler item = new ItemCommentCrawler();
		// try {
		// item.start(13283073201l, 1);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
}
