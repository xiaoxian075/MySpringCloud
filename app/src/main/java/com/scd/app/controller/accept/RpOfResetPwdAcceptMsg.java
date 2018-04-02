package com.scd.app.controller.accept;

import com.scd.sdk.util.GeneralValidationUtil;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RpOfResetPwdAcceptMsg extends BaseAcceptMsg {

	/**
	 * 密码格式为字符、数字、字母任意两种组合，长度为8-20位
	 */
	private String newPassword;

	@Override
	public boolean check() {
		if (!GeneralValidationUtil.password(newPassword, 8, 20)) {
			return false;
		}
		return true;
	}
	
}
