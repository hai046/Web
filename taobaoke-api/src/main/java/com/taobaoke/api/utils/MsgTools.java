package com.taobaoke.api.utils;

import com.alibaba.fastjson.JSONObject;

public class MsgTools {
	// public static JSONObject createOKJSON(){
	// JSONObject json = new JSONObject();
	// json.put("op_status", "OK");
	// return json;
	// }

	public static JSONObject createOKJSON(Object object) {
		JSONObject json = new JSONObject();
		json.put("op_status", "OK");
		if (object != null /*
							 * &&( object instanceof JSONObject || object
							 * instanceof JSONArray )
							 */) {
			json.put("data", object);
		}
		return json;
	}

	public static JSONObject createErrorJSON(String errorCode, String errorMsg) {
		JSONObject json = new JSONObject();
		json.put("op_status", "NG");
		json.put("err_code", Integer.parseInt(errorCode));
		json.put("err_msg", errorMsg);
		return json;
	}

	/**
	 * ==========================================<BR>
	 * 功能：实现错误打印 <BR>
	 * 时间：2013-1-28 下午4:48:29 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param errorCode
	 * @param errorMsg
	 * @param developerMsg
	 *            此为系统错误信息，请不要公开显示
	 * @return
	 */
	public static JSONObject createErrorJSON(String errorCode, String errorMsg,
			String developerMsg) {
		JSONObject json = new JSONObject();
		json.put("op_status", "NG");
		json.put("err_code", Integer.parseInt(errorCode));
		json.put("err_msg", errorMsg);
		json.put("developer_msg", "此信息不对用户显示：" + developerMsg);
		return json;
	}

	public static void main(String[] args) throws Exception {

	}
}
