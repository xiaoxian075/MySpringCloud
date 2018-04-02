package com.scd.mweb.controller.accept;


import com.scd.sdk.util.GeneralValidationUtil;
import com.scd.sdk.util.pojo.H5BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegOfGetCodeAcceptMsg extends H5BaseAcceptMsg {

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

