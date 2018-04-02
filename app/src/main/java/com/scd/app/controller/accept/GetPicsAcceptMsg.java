package com.scd.app.controller.accept;


import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPicsAcceptMsg extends BaseAcceptMsg {
	
	/**
	 * 类型 1：云社区 2：零距离
	 */
	private int type;

	@Override
	public boolean check() {
//		if (type != Constant.COMMUNITY && type != Constant.ZERO_DISTANCE) {
//			return false;
//		}
		if (!Type.isSowing(type)) {
			return false;
		}
		return true;
	}
}


