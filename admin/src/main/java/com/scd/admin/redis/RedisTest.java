package com.scd.admin.redis;

import com.scd.sdk.redis.RedisInter;

public class RedisTest implements RedisInter {

	@Override
	public void excute(String topic, Integer msgId, String data, Long timestamp) {
		System.out.println("topic:" + topic);
		System.out.println("msgId:" + msgId);
		System.out.println("data:" + data);
		System.out.println("timestamp:" + timestamp);
	}

}
