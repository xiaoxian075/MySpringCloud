package com.scd.mweb.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Html5BaseVo {

	private int code;
	private String msg;
	private Object info;
	private long timestamp;
	
	public Html5BaseVo() {
		this.code = 0;
		this.msg = "";
		this.info = null;
		this.timestamp = System.currentTimeMillis();
	}
}
