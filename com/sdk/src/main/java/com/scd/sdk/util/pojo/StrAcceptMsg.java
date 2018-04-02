package com.scd.sdk.util.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrAcceptMsg extends BaseAcceptMsg {
	
	private String str;

	@Override
	public boolean check() {
		if (str == null) {
			return false;
		}

		return true;
	}

}
