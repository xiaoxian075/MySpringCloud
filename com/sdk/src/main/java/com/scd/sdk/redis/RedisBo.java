package com.scd.sdk.redis;

import java.lang.reflect.Type;

import com.scd.sdk.util.GsonUtil;


/**
 * @ClassName RedisBo
 * @Description
 * @author chenjx
 * @date 2017年10月17日 下午9:51:37
 */
public class RedisBo<T> {
	public static <T> String stringRedis(String topic, int id, T data) {
		return GsonUtil.toString(new RedisBo<T>(topic, id, data));
	}
	
	public static <T> RedisBo<T> createRedis(String topic, int id, T data) {
		return new RedisBo<T>(topic, id, data);
	}
	
	public static <T> RedisBo<T> createRedis(String data, Type c) {
		return GsonUtil.toJson(data, c);
	}
	
	private String topic;
	private int msgId;
	private T data;
	private long timestamp;
	public RedisBo() {
		super();
	}
	public RedisBo(String topic, int msgId, T data) {
		super();
		this.topic = topic;
		this.msgId = msgId;
		this.data = data;
		this.timestamp = System.currentTimeMillis();
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
