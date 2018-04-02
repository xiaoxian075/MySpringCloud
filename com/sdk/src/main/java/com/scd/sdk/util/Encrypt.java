package com.scd.sdk.util;

import com.alibaba.fastjson.JSONObject;
import com.scd.sdk.util.pojo.BaseAcceptMsg;
import com.scd.sdk.util.pojo.H5BaseAcceptMsg;
import com.scd.sdk.util.pojo.ReceiveMsg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class Encrypt {
	private static Logger LOGGER = LoggerFactory.getLogger(Encrypt.class);
	
	private final static String KEY_DATA = "data";
	private final static String ZERO_INFO = "";
	private final static int SUCC_CODE = 0;
	private final static String SUCC_DESC = "succ";

	private String desPwd;
	private Encrypt() {
		desPwd = null;
	}

	// 此处使用一个内部类来维护单例
	private static class EncryptFactory {
		private static Encrypt instance = new Encrypt();
	}

	// 获取实例
	public static Encrypt getInstance() {
		return EncryptFactory.instance;
	}
	
	public void initDespwd(String _desPwd) {
		desPwd = _desPwd;
	}

//	// 如果该对象被用于序列化，可以保证对象在序列化前后保持一致
//	public Object readResolve() {
//		return getInstance();
//	}


	public <T extends H5BaseAcceptMsg> T subPackH5(HttpServletRequest request, Class<T> c) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		String data = request.getParameter(KEY_DATA);
		T t = GsonUtil.toJson(data, c);
		if (t == null) {
			return null;
		}
		
		return t;
	}
	
	public <T extends BaseAcceptMsg> T subPack(HttpServletRequest request, Class<T> c) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		String data = request.getParameter(KEY_DATA);
		return subPack(data, c);
	}

	/**
	 * 解包
	 * @param resource
	 * @param c
	 * @return
	 */
	public <T extends BaseAcceptMsg> T subPack(String resource, Class<T> c) {
		if (resource == null) {
			return null;
		}

		String data = null;
		if (StringUtils.isNoneBlank(desPwd)) {
			try {
				data = DesUtil.decrypt(resource, desPwd);
			} catch (Exception e) {
				LOGGER.error("decrypt error:"+e.getMessage());
				return null;
			}
		} else {
			data = resource;
		}

		if (StringUtils.isBlank(data) || data.length() < 2) {
			LOGGER.warn("no encode : " + resource);
			return null;
		}

		T t = null;
		try {
			JSONObject jsonObject = JSONObject.parseObject(data);
			if (jsonObject == null) {
				return null;
			}
			String sessionId = (String)jsonObject.get("sessionId");
			String requestId = (String)jsonObject.get("requestId");
			String sign = (String)jsonObject.get("sign");
			Object obj = jsonObject.get("info");
			if (	sessionId == null ||
					requestId == null ||
					sign == null ||
					obj == null) {
				return null;
			}

			String str = obj.toString();
			t = GsonUtil.toJson(str, c);
			if (t == null) {
				return null;
			}

			t.set(sessionId, requestId);

		} catch(Exception e) {
			System.out.println(data);
			LOGGER.error("JSON数据解析错误:"+e.getMessage());
			return null;
		}

		return t;
	}
	
	public <T extends BaseAcceptMsg> T subNoPack(HttpServletRequest request, Class<T> c) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		String data = request.getParameter(KEY_DATA);
		return subNoPack(data, c);
	}

	/**
	 * 解包
	 * @param resource
	 * @param c
	 * @return
	 */
	public <T extends BaseAcceptMsg> T subNoPack(String resource, Class<T> c) {
		if (resource == null) {
			return null;
		}

		String data = resource;
		
		if (StringUtils.isBlank(data) || data.length() < 2) {
			LOGGER.warn("no encode : " + resource);
			return null;
		}

		T t = null;
		try {
	    	JSONObject jsonObject = JSONObject.parseObject(data);
	    	if (jsonObject == null) {
	    		return null;
	    	}
	    	String sessionId = (String)jsonObject.get("sessionId");
	    	String requestId = (String)jsonObject.get("requestId");
	    	String sign = (String)jsonObject.get("sign");
			Object obj = jsonObject.get("info");
			if (	sessionId == null ||
					requestId == null ||
					sign == null ||
					obj == null) {
				return null;
			}
			
			String str = obj.toString();
			t = GsonUtil.toJson(str, c);
			if (t == null) {
				return null;
			}

			t.set(sessionId, requestId);
			
		} catch(Exception e) {
			System.out.println(data);
			LOGGER.error("JSON数据解析错误:"+e.getMessage());
			return null;
		}
		
		return t;
	}

	
	
	
	
	public String pack() {
		return GsonUtil.toString(new ReceiveMsg(SUCC_CODE, SUCC_DESC, ZERO_INFO));
	}
	
//	public static String pack(Status status) {
//		return GsonUtil.toString(new Recmsg(status.getCode(), status.getDesc(), ZERO_INFO));
//	}
	
	public String pack(int code, String desc) {
		return GsonUtil.toString(new ReceiveMsg(code, desc, ZERO_INFO));
	}
	
	public <T> String pack(T t) {
		String info = "";
		if (t != null) {
			info = GsonUtil.toString(t);
			if (info != null && StringUtils.isNoneBlank(desPwd)) {
				try {
					info = DesUtil.encrypt(info, desPwd);
				} catch (Exception e) {
					LOGGER.error("decrypt error:"+e.getMessage());
					return null;
				}
			}
		}
		return GsonUtil.toString(new ReceiveMsg(SUCC_CODE, SUCC_DESC, info));
	}
	
	public <T> String noPack(T t) {
		String info = "";
		if (t != null) {
			info = GsonUtil.toString(t);
		}
		return GsonUtil.toString(new ReceiveMsg(SUCC_CODE, SUCC_DESC, info));
	}
}

