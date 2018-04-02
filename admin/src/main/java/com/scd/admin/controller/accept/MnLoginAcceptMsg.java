package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnLoginAcceptMsg extends BaseAcceptMsg {
	
	private String loginName;
	private String password;

	@Override
	public boolean check() {
		if (loginName == null || loginName.length() == 0) {
			return false;
		}
		if (password == null || password.length() == 0) {
			return false;
		}
		return true;
	}

}
