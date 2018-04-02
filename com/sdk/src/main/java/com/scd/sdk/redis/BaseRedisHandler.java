package com.scd.sdk.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public abstract class BaseRedisHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public final static String FUNCTION_NAME = "handleMessage";
	
	private final static String TOPIC = "topic";
	private final static String MSGID = "msgId";
	private final static String DATA = "data";
	private final static String TIMESTAMP = "timestamp";
	
	
	public void handleMessage(String data) {
    	if (data == null || data.length() == 0) {
    		logger.info("mq data is null");
    		return;
    	}
    	try {
	    	JSONObject jsonObject = JSONObject.parseObject(data);
	    	String topic = (String)jsonObject.get(TOPIC);
	    	Integer msgId = (Integer)jsonObject.get(MSGID);
	    	Object obj = jsonObject.get(DATA);
	    	Long timestamp = (Long)jsonObject.get(TIMESTAMP);
	    	if (msgId == null) {
	    		return;
	    	}
	    	
	    	String strData = null;
	    	if (obj != null) {
	    		strData = obj.toString();
	    	}

	    	RedisInter iRedis = create(msgId);
			if (iRedis != null) {
				iRedis.excute(topic, msgId, strData, timestamp);
			}
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    	}
    }
	
	protected abstract RedisInter create(int msgId);
}
