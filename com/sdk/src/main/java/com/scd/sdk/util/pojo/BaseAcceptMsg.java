package com.scd.sdk.util.pojo;

public abstract class BaseAcceptMsg {
	
	public abstract boolean check();

	public void set(String sessionId, String requestId) {
		this.sessionId = sessionId;
		this.requestId = requestId;
	}
	
	private String sessionId;	// 会话标识
	private String requestId;	// 提交标识（用于判断是否重复提交）
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}


