package com.taobaoke.www.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MsgTools {
//	public static JSONObject createOKJSON(){
//		JSONObject json = new JSONObject();
//		json.put("op_status", "OK");
//		return json;
//	}
    private MsgTools(){}
	
	public static JSONObject createOKJSON(Object object){
		JSONObject json = new JSONObject();
		json.put("op_status", "OK");
		if( object != null &&( object instanceof JSONObject || object instanceof JSONArray ) ){
			json.put("data", object);
		}
		
		return json;
	}
	
	
	public static JSONObject createErrorJSON(String errorCode, String errorMsg){
		JSONObject json = new JSONObject();
		json.put("op_status", "NG");
		json.put("err_code", Integer.parseInt(errorCode));
		json.put("err_msg", errorMsg);
		return json;
	}
	
	public static void main(String []args) throws Exception{
		
	}
}
