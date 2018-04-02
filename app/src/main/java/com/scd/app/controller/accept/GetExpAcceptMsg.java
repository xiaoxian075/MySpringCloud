package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetExpAcceptMsg extends BaseAcceptMsg {
	
	private long commodityId;
	private long addressId;

	@Override
	public boolean check() {
		if (commodityId < 0 || addressId < 0) {
			return false;
		}

		return true;
	}

}
