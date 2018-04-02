package com.scd.sdk.redis;

/**
 * @ClassName 
 * @Description 
 * @author chenjx
 * @date 2017年10月16日 上午9:57:51
 */
public interface RedisInter {
	/**
	 * 定时处理接口
	 * @param topic
	 * @param msgId
	 * @param data
	 * @param timestamp
	 */
	void excute(String topic, Integer msgId, String data, Long timestamp);
}
