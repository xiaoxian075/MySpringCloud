package com.scd.async.constant;

import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.Return;

public class Constant {
	
//	
//	public static int getPage(int page) {
//		if (page <= 0) {
//			return 0;
//		} else if (page > 999999999) {
//			return 999999999;
//		} else {
//			return page - 1;
//		}
//	}
//	
//	public static int getSize(int limit) {
//		if (limit <= 0) {
//			return 20;
//		}
//		if (limit > 100) {
//			return 100;
//		}
//		
//		return limit;
//	}

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
