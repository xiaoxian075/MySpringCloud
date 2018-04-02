package com.scd.async.third.express.kd100;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scd.sdk.util.HttpUtil;

public class Kd100Util {
	private static Logger logger = LoggerFactory.getLogger(Kd100Util.class);

	public static Kd100Response queryExpress(String url, String key, String code, String orderNo) {
		Kd100Response kd100Response = null;
		try {
			String _url = url + "?id=" + key + "&com=" + code + "&nu=" + orderNo + "&show=0&muti=1&order=desc";
	    	String data = HttpUtil.httpGet(_url);
	    	if (data == null) {
	    		return null;
	    	}
	    	
	    	JSONObject jsonObject = JSON.parseObject(data);
	    	JSON json = (JSON) JSON.toJSON(jsonObject);
	    	kd100Response = JSON.toJavaObject(json, Kd100Response.class);
	    	
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
		return kd100Response;
	}
}
