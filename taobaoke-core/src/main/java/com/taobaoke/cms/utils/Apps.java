package com.taobaoke.cms.utils;

/**
 * 当前开发的应用
 * 
 * @author 邓海柱<br>
 *         E-mail:denghaizhu@brunjoy.com
 */
public enum Apps {

	/**
	 * 淘色
	 */
	TAOSE(0),
	/**
	 * 宠物
	 */
	PET(1),
	/**
	 * 未知
	 */
	UNKNOW(-1);
	public static int MAXAPPID = 1;
	private int app_id;

	public int getAppId() {
		return app_id;
	}

	public static Apps getApp(int app_id) {
		switch (app_id) {
		case 0:
			return Apps.TAOSE;
		case 1:
			return Apps.PET;
		default:
			return Apps.UNKNOW;
		}
	}

	/**
	 * ==========================================<BR>
	 * 功能： 获取当前应用的名字 <BR>
	 * 时间：2013-2-19 下午5:48:53 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @return
	 */
	public String getAppName() {
		switch (app_id) {
		case 0:
			return "淘色";
		case 1:
			return "宠物应用";
		default:
			return "";
		}
	}

	Apps(int app_id) {
		this.app_id = app_id;

	}
}
