package com.scd.sdk.util.pojo;

public class ReceiveMsg {

	private int code;		// 错误码 0表示正确
	private String msg;		// 错误信息
	private String info;	// 具体内容
	private long timestamp;	// 时间戮
	public ReceiveMsg() {
		super();
	}
	public ReceiveMsg(int code, String msg, String info) {
		super();
		this.code = code;
		this.msg = msg;
		this.info = info;
		this.timestamp = System.currentTimeMillis();
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}

