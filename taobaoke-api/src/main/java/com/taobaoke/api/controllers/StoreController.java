package com.taobaoke.api.controllers;

import java.sql.SQLException;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.taobaoke.api.utils.MsgTools;
import com.taobaoke.cms.home.SourceStoreHome;

@Path("store")
@Component
public class StoreController {
	@Autowired
	private SourceStoreHome mSourceStoreHome;

	@Post("collects")
	@Get("collects")
	public String collects(@Param("numiids") long numiids[],
			@Param("token") String token) {
		if (token == null) {
			return "@" + MsgTools.createErrorJSON("100001", "设备Token不能为空");
		}
		if (numiids.length < 1) {
			try {
				mSourceStoreHome.deleteBysource_key(token,
						SourceStoreHome.TYPE_COLLECT);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "@" + MsgTools.createOKJSON("删除数据成功");
		}

		JSONArray jsonArray = new JSONArray();
		for (long id : numiids) {
			jsonArray.add(id);
		}
		try {
			mSourceStoreHome.insertOrUpdateValue(token, jsonArray.toString(),
					SourceStoreHome.TYPE_COLLECT);
		} catch (SQLException e) {

			e.printStackTrace();
			return "@" + MsgTools.createErrorJSON("100002", "保存失败");
		}
		return "@" + MsgTools.createOKJSON(null);
	}

	@Post("getCollects")
	@Get("getCollects")
	public String getCollects(@Param("token") String token) {
		if (token == null) {
			return "@" + MsgTools.createErrorJSON("100001", "设备Token不能为空");
		}
		try {
			String vaule = mSourceStoreHome.getSourceValue(token,
					SourceStoreHome.TYPE_COLLECT);
			return "@" + MsgTools.createOKJSON(vaule);
		} catch (SQLException e) {
			e.printStackTrace();
			return "@" + MsgTools.createErrorJSON("100002", e.getMessage());
		}

	}

	@Post("store")
	@Get("store")
	public String store(@Param("vaule") String vaule,
			@Param("token") String token) {
		if (token == null) {
			return "@" + MsgTools.createErrorJSON("100001", "设备Token不能为空");
		}
		if (vaule == null) {
			try {
				mSourceStoreHome.deleteBysource_key(token,
						SourceStoreHome.TYPE_OTHER);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "@" + MsgTools.createOKJSON("删除数据成功");
		}
		try {
			mSourceStoreHome.insertOrUpdateValue(token, vaule,
					SourceStoreHome.TYPE_OTHER);
		} catch (SQLException e) {

			e.printStackTrace();
			return "@" + MsgTools.createErrorJSON("100002", "保存失败");
		}
		return "@" + MsgTools.createOKJSON(null);
	}

	@Post("getStore")
	@Get("getStore")
	public String getStore(@Param("token") String token) {
		if (token == null) {
			return "@" + MsgTools.createErrorJSON("100001", "设备Token不能为空");
		}
		try {
			String vaule = mSourceStoreHome.getSourceValue(token,
					SourceStoreHome.TYPE_OTHER);
			return "@" + MsgTools.createOKJSON(vaule);
		} catch (SQLException e) {
			e.printStackTrace();
			return "@" + MsgTools.createErrorJSON("100002", e.getMessage());
		}

	}

}
