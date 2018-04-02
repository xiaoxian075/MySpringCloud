package com.scd.admin.mgr;

import org.apache.commons.lang3.StringUtils;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import com.scd.sdk.util.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SessionMgr {
	private Map<String, Session> mapSession = new HashMap<String, Session>();
	
	private SessionMgr() {}
	private static class SessionMgrFactory {
		private static SessionMgr instance = new SessionMgr();
	}
	public static SessionMgr getInstance() {
		return SessionMgrFactory.instance;
	}
	
	public String getSessionByAccount(String sessionId, String account) {
		for (Entry<String, Session> entry : mapSession.entrySet()) {
			Session session = entry.getValue();
			if (session.getAccount().equals(account) && !session.getSessionId().equals(sessionId)) {
				return session.getSessionId();
			}
		}
		return null;
	}
	
	public Session getBySession(String sessionId) {
		if (sessionId != null) {
			return mapSession.get(sessionId);
		}
		return null;
	}
	
	public void add(Session session) {
		if (session != null) {
			mapSession.put(session.getSessionId(), session);
		}
	}
	
	public Session remove(String sessionId) {
		if (sessionId != null) {
			return mapSession.remove(sessionId);
		}
		return null;
	}

	public <T extends BaseAcceptMsg> Session get(T t) {
		if (t == null) {
			return null;
		}
		String sessionId = t.getSessionId();
		if (StringUtils.isBlank(sessionId)){
			return null;
		}
		return mapSession.get(sessionId);
	}
	
	public Session get(String sessionId) {
		if (StringUtils.isBlank(sessionId)){
			return null;
		}
		return mapSession.get(sessionId);
	}
	
	public <T extends BaseAcceptMsg> Session remove(T t) {
		if (t == null) {
			return null;
		}
		String sessionId = t.getSessionId();
		if (StringUtils.isBlank(sessionId)){
			return null;
		}
		return mapSession.remove(sessionId);
	}

	private static final Session defaultSession = new Session();
	public <T extends BaseAcceptMsg> Session checkSession(T t) {
		Session session = get(t);
		if (session == null) {
			return defaultSession;
		} else {
			return session;
		}
//		
//		long curTime = System.currentTimeMillis();
//		if (session.isTimeout(curTime)) {
//			mapSession.remove(session.getSessionId());
//			return null;
//		}
//		
//		return session;
	}
	
	public <T extends BaseAcceptMsg> Session getSession(T t) {
		Session session = get(t);
		if (session == null) {
			return null;
		}
		
		long curTime = System.currentTimeMillis();
		if (session.isTimeout(curTime)) {
			mapSession.remove(session.getSessionId());
			return null;
		}
		
		return session;
	}
	
	public <T extends BaseAcceptMsg> Session checkLogin(T t) {
		Session session = get(t);
		if (session == null) {
			return null;
		}
		
		long curTime = System.currentTimeMillis();
		if (session.isTimeout(curTime)) {
			mapSession.remove(session.getSessionId());
			return null;
		}
		
		String account = session.getAccount();
		if (account == null || account.length() == 0) {
			return null;
		}
		
		return session;
	}
	
	public void timer(long curTime) {
		List<String> sessionList = new ArrayList<String>();
		for (Entry<String, Session> entry : mapSession.entrySet()) {
			Session session = entry.getValue();
			if (session.isTimeout(curTime)) {
				sessionList.add(session.getSessionId());
			}
		}
		
		for (String sessionId : sessionList) {
			mapSession.remove(sessionId);
		}
	}
}

