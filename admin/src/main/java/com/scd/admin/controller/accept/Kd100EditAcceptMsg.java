package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Kd100EditAcceptMsg extends BaseAcceptMsg {
	
	private long id;
	private String name;
	private String code;

	@Override
	public boolean check() {
		if (	id <=0 ||
				name == null || name.trim().length() == 0 || name.trim().length() > 64 ||
				code == null || code.trim().length() == 0 || code.trim().length() > 32) {
			return false;
		}
		return true;
	}

}
