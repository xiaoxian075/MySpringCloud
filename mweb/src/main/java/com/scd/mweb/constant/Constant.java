package com.scd.mweb.constant;

import javax.servlet.http.HttpServletRequest;

import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.Encrypt;
import com.scd.sdk.util.pojo.H5BaseAcceptMsg;
import com.scd.sdk.util.pojo.Return;

public class Constant {
	// session 对象的类型
	public final static int REGISTER_CODE = 1;		// 注册验证码
	public final static int RESETPWD_CODE = 2;		// 找回密码验证码
	
	
	public static int getPage(int page) {
		if (page <= 0) {
			return 0;
		} else if (page > 999999999) {
			return 999999999;
		} else {
			return page - 1;
		}
	}
	
	public static int getSize(int limit) {
		if (limit <= 0) {
			return 20;
		}
		if (limit > 100) {
			return 100;
		}
		
		return limit;
	}
	
	public static <T extends H5BaseAcceptMsg> T subPack(HttpServletRequest request, Class<T> c) {
		return Encrypt.getInstance().subPackH5(request, c);
	}

	public static String pack(ErrorCom status) {
		return Encrypt.getInstance().pack(status.getCode(), status.getDesc());
	}
	
	public static <T> String pack(Return<T> ret) {
		return Encrypt.getInstance().pack(ret.getCode(), ret.getDesc());
	}
	
	public static <T> String pack(T t) {
		return Encrypt.getInstance().noPack(t);
	}
	
	public static String pack() {
		return Encrypt.getInstance().pack();
	}

	
	
	
	public static <T> Return<T> createReturn(ErrorCom status) {
		return new Return<T>(status.getCode(), status.getDesc(), null);
	}
	
	public static <T> Return<T> createReturn(int code, String desc) {
		return new Return<T>(code, desc, null);
	}
	
	public static <T> Return<T> createReturn() {
		return new Return<T>(ErrorCom.SUCCESS.getCode(), ErrorCom.SUCCESS.getDesc(), null);
	}

	public static <T> Return<T> createReturn(T t) {
		return new Return<T>(ErrorCom.SUCCESS.getCode(), ErrorCom.SUCCESS.getDesc(), t);
	}
}
