package com.scd.admin.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarouselAddAcceptMsg extends BaseAcceptMsg {
	
	private int type;
	private String url;

	@Override
	public boolean check() {
		if (!Type.isSowing(type)) {
			return false;
		}
		if (url == null || url.trim().length() == 0 || url.trim().length() > 255) {
			return false;
		}
		return true;
	}

}


