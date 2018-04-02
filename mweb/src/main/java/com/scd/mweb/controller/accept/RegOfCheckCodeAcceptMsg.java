package com.scd.mweb.controller.accept;

import com.scd.sdk.util.pojo.H5BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegOfCheckCodeAcceptMsg extends H5BaseAcceptMsg {

	private String phone;

	private String code;

	@Override
	public boolean check() {
//		if (!GeneralValidationUtil.code(code, 6)) {
//			return false;
//		}
		return true;
	}
}
