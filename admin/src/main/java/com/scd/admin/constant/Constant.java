package com.scd.admin.constant;

import javax.servlet.http.HttpServletRequest;

import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.Encrypt;
import com.scd.sdk.util.pojo.BaseAcceptMsg;
import com.scd.sdk.util.pojo.Return;

public class Constant {
	
	
	public static <T extends BaseAcceptMsg> T subPack(HttpServletRequest request, Class<T> c) {
		return Encrypt.getInstance().subPack(request, c);
	}
//	public static String pack(ErrorStatus status) {
//		return Encrypt.getInstance().pack(status.getCode(), status.getDesc());
//	}
	
	public static String pack(ErrorCom status) {
		return Encrypt.getInstance().pack(status.getCode(), status.getDesc());
	}
	
	public static <T> String pack(Return<T> ret) {
		return Encrypt.getInstance().pack(ret.getCode(), ret.getDesc());
	}
	
	public static <T> String pack(T t) {
		return Encrypt.getInstance().pack(t);
	}
	
	public static String pack() {
		return Encrypt.getInstance().pack();
	}

	public static <T> Return<T> createReturn(ErrorCom status) {
		return new Return<T>(status.getCode(), status.getDesc(), null);
	}
	
//	public static <T> Return<T> createReturn(ErrorStatus status) {
//		return new Return<T>(status.getCode(), status.getDesc(), null);
//	}
	
	public static <T> Return<T> createReturn(int code, String desc) {
		return new Return<T>(code, desc, null);
	}
	
	public static <T> Return<T> createReturn() {
		return new Return<T>(ErrorCom.SUCCESS.getCode(), ErrorCom.SUCCESS.getDesc(), null);
	}

	public static <T> Return<T> createReturn(T t) {
		return new Return<T>(ErrorCom.SUCCESS.getCode(), ErrorCom.SUCCESS.getDesc(), t);
	}

//	public static <T> Return<T> createReturn(ErrorCom status) {
//		return new Return<T>(status.getCode(), status.getDesc(), null);
//	}
//	
//	public static <T> Return<T> createReturn(ErrorStatus status) {
//		return new Return<T>(status.getCode(), status.getDesc(), null);
//	}
//	
//	public static <T> Return<T> createReturn(int code, String desc) {
//		return new Return<T>(code, desc, null);
//	}
//	
//	public static <T> Return<T> createReturn() {
//		return new Return<T>(ErrorStatus.SUCCESS.getCode(), ErrorStatus.SUCCESS.getDesc(), null);
//	}
//
//	public static <T> Return<T> createReturn(T t) {
//		return new Return<T>(ErrorStatus.SUCCESS.getCode(), ErrorStatus.SUCCESS.getDesc(), t);
//	}
}
