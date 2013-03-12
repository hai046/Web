package com.taobaoke.cms.utils;


public class CookieUtil {

	private static int app_id = -1;
	
	public static int getApp_id() {
		return app_id;
	}

	public static void setApp_id(int app_id) {
		CookieUtil.app_id = app_id;
	}

}
