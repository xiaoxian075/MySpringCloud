package com.scd.app.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VersionTypeAcceptMsg extends BaseAcceptMsg {
	
	private int type;

	@Override
	public boolean check() {
		if (!Type.checkAppType(type)) {
			return false;
		}

		return true;
	}

}
