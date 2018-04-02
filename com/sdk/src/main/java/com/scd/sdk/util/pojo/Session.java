package com.scd.sdk.util.pojo;

import java.util.HashMap;
import java.util.Map;

public class Session {

	private String clientIp;
	private String sessionId;
	private String requestId;
	private String account;
	private long lastTime;
	private long timeout;
	
	private Map<Integer, Object> mapObj;
	
	
	
	public Session() {
		super();
	}
	/**
	 * 
	 * @param clientIp
	 * @param sessionId
	 * @param timeOut  单位为秒
	 */
	public Session(String clientIp, String sessionId, int timeOut) {
		super();
		this.clientIp = clientIp;
		this.sessionId = sessionId;
		this.requestId = "";
		this.account = "";
		this.lastTime = System.currentTimeMillis();
		// 转换成毫秒
		this.timeout = timeOut*1000;
		
		this.mapObj = new HashMap<Integer, Object>();
	}
	
	/**
	 * 
	 * @param clientIp
	 * @param sessionId
	 * @param timeOut  单位为秒
	 */
	public Session(String clientIp, String sessionId, int timeOut, String account) {
		super();
		this.sessionId = sessionId;
		this.requestId = "";
		this.account = account;
		this.lastTime = System.currentTimeMillis();
		// 转换成毫秒
		this.timeout = timeOut*1000;
		
		this.mapObj = new HashMap<Integer, Object>();
	}

	public String getClientIp() {
		return clientIp;
	}
	public String getSessionId() {
		return sessionId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public long getLastTime() {
		return lastTime;
	}
	
	
	public void addObj(int type, Object obj) {
		mapObj.put(type, obj);
	}
	
	public Object getObj(int type) {
		return mapObj.get(type);
	}
	
	
	
	public boolean isTimeout(long curTime) {
		if (lastTime + timeout < curTime) {
			return true;
		} else {
			lastTime = curTime;
			return false;
		}
	}
	public boolean isLogin() {
		if (account != null || account.length() == 0) {
			return true;
		}
		return false;
	}
}
