package com.scd.sdk.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * @ClassName GsonUtil
 * @Description
 * @author chenjx
 * @date 2017年10月20日 下午2:51:18
 */
public class GsonUtil {

	private static Gson gson = new Gson();
	
	/**
	 * 转换成JSON字符串
	 * @param t
	 * @return
	 */
	public static <T> String toString(T t) {
		return gson.toJson(t);
	}
	
	/**
	 * 转换成对象
	 * 泛型：new TypeToken<List<Integer>>() {}.getType()
	 * @param data
	 * @param c
	 * @return
	 */
	public static <T> T toJson(String data, Type c) {
		try{
			return gson.fromJson(data, c);
		} catch (Exception e) {
			return null;
		}
	}
}
