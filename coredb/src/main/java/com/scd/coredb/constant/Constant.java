package com.scd.coredb.constant;

import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.pojo.Return;

public class Constant {
	
	private static String defaultReturn = GsonUtil.toString(new Return<Object>(ErrorCom.SUCCESS.getCode(), ErrorCom.SUCCESS.getDesc(), null));
	
	public static <T> Return<T> createReturn() {
		return new Return<T>(ErrorCom.SUCCESS.getCode(), ErrorCom.SUCCESS.getDesc(), null);
	}
	
	public static <T> Return<T> createReturn(int code, String desc) {
		return new Return<T>(code, desc, null);
	}
	
	public static <T> Return<T> createReturn(ErrorCom status) {
		return new Return<T>(status.getCode(), status.getDesc(), null);
	}
	
	public static <T> Return<T> createReturn(T t) {
		return new Return<T>(ErrorCom.SUCCESS.getCode(), ErrorCom.SUCCESS.getDesc(), t);
	}
	
	
	public static String toStr(ErrorCom status) {
		return GsonUtil.toString(new Return<Object>(status.getCode(), status.getDesc(), null));
	}
	
	public static String toStr(int code, String desc) {
		return GsonUtil.toString(new Return<Object>(code, desc, null));
	}
	
	public static String toStr() {
		return defaultReturn;
	}

	public static <T> String toStr(T t) {
		return GsonUtil.toString(new Return<T>(ErrorCom.SUCCESS.getCode(), ErrorCom.SUCCESS.getDesc(), t));
	}
}
