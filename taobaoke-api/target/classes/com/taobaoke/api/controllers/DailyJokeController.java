package com.taobaoke.api.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobaoke.api.utils.IosUtil;
import com.taobaoke.cms.home.DailyJokeHome;
import com.taobaoke.cms.model.DailyJoke;

@Path("joke")
public class DailyJokeController {

	@Autowired
	private DailyJokeHome mDailyJokeHome;

	@Get("get")
	public String getJoke(@Param("date") Date date, @Param("limit") int limit,
			@Param("offset") int offset, @Param("app_key") String app_key,
			@Param("v") int v) {

		JSONObject json = new JSONObject();
		// TODO fix ios
		json.put("op_status", "OK");
		if (IosUtil.isIosIPhoneDev(app_key, v)
				|| IosUtil.isIosIPadDev(app_key, v)) {
			List<DailyJoke> list = new ArrayList<DailyJoke>();
			DailyJoke mDailyJoke = mDailyJokeHome.getById(38);
			if (mDailyJoke != null)
				list.add(mDailyJoke);
			JSONArray arrays = getJokeList(list);
			json.put("data", arrays);
			json.put("passed", false);// iPhone v=2没有通过审核 //2013-1-11

		} else {
			List<DailyJoke> list = null;
			if (date == null) {
				list = mDailyJokeHome.getAllBeforeNow(offset, limit);
			} else {
				list = mDailyJokeHome.getAll(date, offset, limit);
			}
			JSONArray arrays = getJokeList(list);
			json.put("data", arrays);
			json.put("passed", true);
		}

		return "@" + json;
	}

	private JSONArray getJokeList(List<DailyJoke> list) {
		JSONArray arrays = new JSONArray();

		if (list != null) {
			for (DailyJoke mDailyJoke : list) {
				JSONObject obj = getJokeObj(mDailyJoke);
				arrays.add(obj);
			}
		}

		return arrays;
	}

	private JSONObject getJokeObj(DailyJoke mDailyJoke) {
		JSONObject obj = new JSONObject();
		obj.put("date", mDailyJoke.getDate());
		obj.put("content", mDailyJoke.getContent());
		return obj;
	}

}
