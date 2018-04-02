package com.scd.app.controller.accept;

import com.scd.sdk.util.GeneralValidationUtil;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegOfPwdARefereeAcceptMsg extends BaseAcceptMsg {

	/**
	 * 密码格式为字符、数字、字母任意两种组合，长度为8-20位
	 */
	private String password;
	
	/**
	 * 推荐人支持字母、数字、中文输入
	 */
	private String referee;

	@Override
	public boolean check() {
		if (!checkPassword()) {
			return false;
		}
		if (!checkReferee()) {
			return false;
		}
		return true;
	}
	
	public boolean checkPassword() {
		if (!GeneralValidationUtil.password(password, 8, 20)) {
			return false;
		}
		return true;
	}
	
	public boolean checkReferee() {
		if (referee == null || referee.length() == 0) {
			// 推荐人允许为空
			referee = null;
			return true;
		}
		if (!GeneralValidationUtil.name(referee, 2, 10)) {
			return false;
		}
		return true;
	}
}
