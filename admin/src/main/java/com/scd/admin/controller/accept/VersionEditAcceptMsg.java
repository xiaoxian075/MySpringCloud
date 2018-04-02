package com.scd.admin.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VersionEditAcceptMsg extends BaseAcceptMsg {
	
	private long id;
	private String version;
	private int state;

	@Override
	public boolean check() {
		if (id <= 0) {
			return false;
		}
		
		if (version != null && version.length() == 0) {
			version = null;
		}
		
		if (state != 0 && !Type.checkVersion(state)) {
			return false;
		}

		return true;
	}

}

