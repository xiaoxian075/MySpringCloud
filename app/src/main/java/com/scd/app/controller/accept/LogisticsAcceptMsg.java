package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogisticsAcceptMsg extends BaseAcceptMsg {
	
	private String code;
	private String name;
	private String odd;

	@Override
	public boolean check() {
		if (
				code == null || code.length() == 0 || code.length() > 64 ||
				name == null || name.length() == 0 || name.length() > 255 || 
				odd == null || odd.length() == 0 || odd.length() > 255) {
			return false;
		}
		return true;
	}

}

