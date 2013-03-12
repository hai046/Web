package com.taobaoke.api.controllers;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobaoke.api.utils.MsgTools;
import com.taobaoke.cms.home.ExtraHome;
import com.taobaoke.cms.model.ExtraItem;
import com.taobaoke.cms.redis.RedisPoolFactory;

@Path("system")
public class SystemController {
	// @Autowired
	// private ExtraHome mExtraHome;
	private final int DEFAULT_SIZE = 40;

	@Get("getLoadingPic")
	public String getLoadingPic(@Param("width") int width,
			@Param("height") int height, @Param("app_key") String app_key) {

		String extraInfo = "_" + width + "x" + height;
		String key = "loadingPic";
		ExtraItem info;
		try {
			info = ExtraHome.getInstance().getByKey(key + extraInfo);
			if (info == null) {
				info = ExtraHome.getInstance().getByKey(key);
			}
			if (info != null) {
				JSONObject obj = new JSONObject();
				obj.put("key", info.getKey());
				obj.put("value", info.getValue());
				obj.put("name", info.getName());
				return "@" + MsgTools.createOKJSON(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "@" + MsgTools.createErrorJSON("70001", "没有该信息");
	}

	@Get("deleteCache")
	public String deleteCache(Invocation inv, @Param("key") String key,
			@Param("front") String front, @Param("from") int from,
			@Param("to") int to, @Param("last") String last) {
		if (key == null && from < to && from >= 0) {
			for (int i = from; i < to; i++) {
				RedisPoolFactory.delete(front + i + to);

			}
			return MsgTools.createOKJSON(
					"deleteCache  from from=" + from + "  to=" + to)
					.toJSONString();
		} else {
			RedisPoolFactory.delete(key);
		}
		return "";
	}

	@Get("getValueByKey")
	public String getValueByKey(@Param("key") String key) {

		do {
			if (key == null || key.trim().length() < 1)
				break;
			ExtraItem info;
			try {
				info = ExtraHome.getInstance().getByKey(key);
				if (info != null) {
					JSONObject obj = new JSONObject();
					obj.put("key", info.getKey());
					obj.put("value", info.getValue());
					obj.put("name", info.getName());
					return "@" + MsgTools.createOKJSON(obj);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} while (false);
		return "@" + MsgTools.createErrorJSON("70002", "没有该信息");
	}

	@Get("list")
	public String getList(@Param("currentPage") int page) {
		try {
			page = page < 1 ? 1 : page;
			int offset = (page - 1) * DEFAULT_SIZE;
			int count = ExtraHome.getInstance().getCount();
			long pageCount = count / (long) DEFAULT_SIZE;
			pageCount = pageCount * DEFAULT_SIZE == count ? pageCount
					: pageCount + 1;
			List<ExtraItem> list = ExtraHome.getInstance().getAll(offset,
					DEFAULT_SIZE);
			if (list != null) {
				JSONArray arrays = new JSONArray();
				for (ExtraItem item : list) {
					arrays.add(item);
				}
				return "@" + MsgTools.createOKJSON(arrays);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "@" + MsgTools.createErrorJSON("70003", "获取信息失败");
	}

}
