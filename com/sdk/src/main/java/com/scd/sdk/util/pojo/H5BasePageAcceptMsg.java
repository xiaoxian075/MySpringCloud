package com.scd.sdk.util.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class H5BasePageAcceptMsg extends H5BaseAcceptMsg {
	protected int page;
	protected int size;
	

	
	protected static int getPage(int page) {
		if (page <= 0) {
			return 0;
		} else if (page > 999999999) {
			return 999999999;
		} else {
			return page - 1;
		}
	}
	
	protected static int getSize(int limit) {
		if (limit <= 0) {
			return 20;
		}
		if (limit > 100) {
			return 100;
		}
		
		return limit;
	}
}
