package com.scd.sdk.util.pojo;

public class Return<T> {
	
	public static <T> boolean isErr(Return<T> ret) {
		if (ret == null) {
			return true;
		}
		
		if (ret.getCode() != 0) {
			return true;
		}
		
		return false;
	}
	
	public static <T> Return<T> createNew(int code, String desc) {
		return new Return<T>(code, desc, null);
	}
	
	public static <T> Return<T> createNew(int code, String desc, T data) {
		return new Return<T>(code, desc, data);
	}
	
	public static <T> Return<T> createNew(T data) {
		return new Return<T>(0, "", data);
	}

	private int code;
	private String desc;
	private T data;
	public Return() {
		super();
	}
	public Return(int code, String desc, T data) {
		super();
		this.code = code;
		this.desc = desc;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}

