package com.scd.sdk.util.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrPageAcceptMsg extends BasePageAcceptMsg {
	
	private String str;
	
	@Override
	public boolean check() {
		if (str == null) {
			return false;
		}
		
		page = getPage(page);
		size = getSize(size);
		return true;
	}
}
