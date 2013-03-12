package com.taobaoke.api.controllers;

import java.util.Date;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.taobaoke.api.utils.MsgTools;
import com.taobaoke.cms.home.SourceCounterHome;
import com.taobaoke.cms.model.SourceCounter;

/**
 * 计数器
 * 
 * @author Haizhu
 * 
 */
@Path("counter")
@Component
public class CounterController {

	@Autowired
	private SourceCounterHome mCounterHome;

	@Post("count")
	@Get("count")
	public String count(@Param("source_id") int source_id,
			@Param("count") int count, @Param("type") int typeValue,
			@Param("date") Date date) {
		SourceCounterHome.Type type = SourceCounterHome.Type.OTHER;
		for (SourceCounterHome.Type t : SourceCounterHome.Type.values()) {
			if (typeValue == t.getTypeValue()) {
				type = t;
				break;
			}
		}
		try {
			mCounterHome.insert(source_id, count, type, date);
			return "@"+MsgTools.createOKJSON(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "@"+MsgTools.createErrorJSON("100001", "添加计数失败");
	}
	
	@Get("getCount")
	public String getCount(@Param("source_id") int source_id,
			@Param("type") int typeValue) {
		SourceCounterHome.Type type = SourceCounterHome.Type.OTHER;
		for (SourceCounterHome.Type t : SourceCounterHome.Type.values()) {
			if (typeValue == t.getTypeValue()) {
				type = t;
				break;
			}
		}
		try {
			SourceCounter mSourceCounter=mCounterHome.getBySource_id(source_id,type);
			JSONObject obj=new JSONObject();
			obj.put("count", mSourceCounter.getCount());
			obj.put("id", mSourceCounter.getId());
			obj.put("source_id", mSourceCounter.getSource_id());
			obj.put("type", mSourceCounter.getType());
			obj.put("update_time", mSourceCounter.getUpdate_time());
			return "@"+MsgTools.createOKJSON(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "@"+MsgTools.createErrorJSON("100001", "获取计数失败");
	}
}
