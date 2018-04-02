package com.scd.app.controller.accept;

import com.scd.sdk.util.GeneralValidationUtil;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegOfCheckCodeAcceptMsg extends BaseAcceptMsg {

	/**
	 * 6位数字验证码
	 */
	private String code;

	@Override
	public boolean check() {
		if (!GeneralValidationUtil.code(code, 6)) {
			return false;
		}
		return true;
	}
}
