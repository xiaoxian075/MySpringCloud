package com.scd.app.controller.accept;


import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PraiseCommunityAcceptMsg extends BaseAcceptMsg {

	/**
	 * 项ID
	 */
	private long id;
	/**
	 * 类型 0:取消点赞 1：点赞
	 */
	private int type;

	@Override
	public boolean check() {
		if (id <= 0) {
			return false;
		}
		if (!Type.isPraise(type)) {
			return false;
		}
		
		return true;
	}
}



