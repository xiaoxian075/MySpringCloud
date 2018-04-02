package com.scd.app.controller.accept;


import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HitCommunityAcceptMsg extends BaseAcceptMsg {

	/**
	 * 项ID
	 */
	private long id;

	@Override
	public boolean check() {
		if (id <= 0) {
			return false;
		}
		
		return true;
	}
}



