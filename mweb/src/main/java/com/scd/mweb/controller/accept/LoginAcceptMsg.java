package com.scd.mweb.controller.accept;


import com.scd.sdk.util.GeneralValidationUtil;
import com.scd.sdk.util.pojo.H5BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAcceptMsg extends H5BaseAcceptMsg {
	
	/**
	 * 字母，数字，中文
	 */
	private String loginName;
	/**
	 * 字母，数字，符号
	 */
	private String password;

	@Override
	public boolean check() {
		if (!GeneralValidationUtil.name(loginName, 2, 20)) {
			return false;
		}
//		if (!GeneralValidationUtil.password(password, 8, 20)) {
//			return false;
//		}
		if (password == null || password.length() < 6) {
			return false;
		}
		return true;
	}
	
	public boolean checkLoginName() {
		if (!GeneralValidationUtil.name(loginName, 2, 20)) {
			return false;
		}
		return true;
	}
	
	public boolean checkPassword() {
//		if (!GeneralValidationUtil.password(password, 8, 20)) {
//			return false;
//		}
		if (password == null || password.length() < 6) {
			return false;
		}
		return true;
	}

}

