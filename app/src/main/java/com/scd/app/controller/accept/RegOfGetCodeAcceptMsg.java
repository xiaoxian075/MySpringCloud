package com.scd.app.controller.accept;


import com.scd.sdk.util.GeneralValidationUtil;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegOfGetCodeAcceptMsg extends BaseAcceptMsg {

	/**
	 * 手机号 11位数字
	 */
	private String phone;

	@Override
	public boolean check() {
		if (!GeneralValidationUtil.phone(phone)) {
			return false;
		}
		return true;
	}

}

