package com.scd.mweb.controller.accept;


import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.H5BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPicsAcceptMsg extends H5BaseAcceptMsg {
	
	/**
	 * 类型 1：云社区 2：零距离
	 */
	private int type;

	@Override
	public boolean check() {
		if (!Type.isSowing(type)) {
			return false;
		}
		return true;
	}
}


