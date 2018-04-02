package com.scd.admin.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BasePageAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPageAcceptMsg extends BasePageAcceptMsg {

	private String account;
	private String odd;
	private int state;
	
	@Override
	public boolean check() {
		if (
				(account != null && account.length()>64) || 
				(odd != null && odd.length()>64) || 
				!(state == 0 || Type.checkOrderState(state))) {
			return false;
		}
		page = getPage(page);
		size = getSize(size);
		return true;
	}

}
