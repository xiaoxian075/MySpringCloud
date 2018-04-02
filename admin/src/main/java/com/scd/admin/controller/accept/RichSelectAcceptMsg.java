package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RichSelectAcceptMsg extends BaseAcceptMsg {

	private long type;
	private long foreignId;
	
	
	
	@Override
	public boolean check() {
		if (
				type <= 0 ||
				foreignId <= 0) {
			return false;
		}
		return true;
	}

}
