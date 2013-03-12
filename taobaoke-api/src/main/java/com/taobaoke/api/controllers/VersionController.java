package com.taobaoke.api.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.taobaoke.api.utils.MsgTools;
import com.taobaoke.cms.home.ExtraHome;
import com.taobaoke.cms.utils.Apps;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("version")
public class VersionController {
	@Autowired
	private ExtraHome mExtraHome;
	private static String androidContent = "androidVerUpdateDesc";
	private static String versionName = "androidVersionName";
	private static String versionCode = "androidVersionCode";
	private static String apkDownUrl = "androidApkDownUrl";

	private static String iPhoneContent = "iPhoneVerUpdateDesc";
	private static String iPhoneversionName = "iPhoneVersionName";
	private static String iPhoneversionCode = "iPhoneVersionCode";
	private static String iPhoneDownUrl = "iPhoneDownUrl";

	private static String iPadContent = "iPadVerUpdateDesc";
	private static String iPadversionName = "iPadVersionName";
	private static String iPadversionCode = "iPadVersionCode";
	private static String iPadDownUrl = "iPadDownUrl";

	@Get("get")
	public String getCurrentVersion(@Param("app_key") String app_key,
			@Param("v") int v, @Param("app_id") int app_id) {
		if (("android_mobile").equalsIgnoreCase(app_key)) {
			return "@" + MsgTools.createOKJSON(getAndroidObject(app_id));
		} else if (("ios_iphone").equalsIgnoreCase(app_key)) {
			return "@" + MsgTools.createOKJSON(getIPhoneObject());
		} else if (("ios_ipad").equalsIgnoreCase(app_key)) {
			return "@" + MsgTools.createOKJSON(getIPadObject());
		}
		return "@" + MsgTools.createErrorJSON("50001", "未知设备");
	}

	private Object getIPadObject() {
		JSONObject obj = new JSONObject();
		try {
			String desc = mExtraHome.getValueByKey(iPadContent);
			if (desc != null) {
				try {
					desc = desc.replaceAll("#", "\n");
				} catch (Exception e) {
				}
			}
			obj.put("content", desc);
			obj.put("name", "淘色");
			obj.put("versionName", mExtraHome.getValueByKey(iPadversionName));
			String vCode = mExtraHome.getValueByKey(iPadversionCode);
			int vc = 0;
			if (vCode == null) {
				vc = 0;
			} else {
				try {
					vc = Integer.parseInt(vCode.trim());
				} catch (Exception e) {
					vc = 0;
				}
			}
			obj.put("versionCode", vc);// 判断是否更新的关键
			obj.put("down_url", mExtraHome.getValueByKey(iPadDownUrl));

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return obj;
	}

	private Object getIPhoneObject() {
		JSONObject obj = new JSONObject();
		try {
			String desc = mExtraHome.getValueByKey(iPhoneContent);
			if (desc != null) {
				try {
					desc = desc.replaceAll("#", "\n");
				} catch (Exception e) {
				}
			}
			obj.put("content", desc);
			obj.put("name", "淘色");
			obj.put("versionName", mExtraHome.getValueByKey(iPhoneversionName));
			String vCode = mExtraHome.getValueByKey(iPhoneversionCode);
			int vc = 0;
			if (vCode == null) {
				vc = 0;
			} else {
				try {
					vc = Integer.parseInt(vCode.trim());
				} catch (Exception e) {
					vc = 0;
				}
			}
			obj.put("versionCode", vc);// 判断是否更新的关键
			obj.put("down_url", mExtraHome.getValueByKey(iPhoneDownUrl));

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject getAndroidObject(int app_id) {
		JSONObject obj = new JSONObject();
		try {
			String desc = mExtraHome.getValueByKey(androidContent+((app_id==0)?"":app_id));
			if (desc != null) {
				try {
					desc = desc.replaceAll("#", "\n");
				} catch (Exception e) {
				}
			}
			obj.put("content", desc);
			obj.put("name", Apps.getApp(app_id).getAppName());
			obj.put("versionName", mExtraHome.getValueByKey(versionName+((app_id==0)?"":app_id)));
			String vCode = mExtraHome.getValueByKey(versionCode+((app_id==0)?"":app_id));
			int vc = 1;// versionCode android 版本号 正整型. android 默认的版本号从1开始
			if (vCode == null) {
				vc = 1;
			} else {
				try {
					vc = Integer.parseInt(vCode.trim());
				} catch (Exception e) {
					vc = 1;
				}
			}
			obj.put("versionCode", vc);// 判断是否更新的关键
			obj.put("down_url", mExtraHome.getValueByKey(apkDownUrl+((app_id==0)?"":app_id)));

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return obj;
	}
}
